package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes.ModelServerResponseDeser;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerRequest;
import com.hayden.utilitymodule.result.Result;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
@DataClient(proto = CodingModelServerContractProto.class)
public class ModelServerCodingAiClient {


    @Builder
    public record CodeResult(String data) {}

    @Autowired(required = false)
    private RestClient modelServerRestClient = RestClient.builder().build();

    private final ModelServerResponseDeser responseDeser;

    @SneakyThrows
    @RequestResponse(requestSource = ModelServerRequest.class, responseSource = ModelServerResponse.class)
    public Result<ModelServerResponse, DataSourceClient.DataSourceClientPrototypeError> send(ModelServerRequest request) {
        return Optional.ofNullable(request.getRetryParameters())
                .filter(r -> r.numRetries() > 1)
                .map(rp -> new RetryTemplateBuilder()
                        .maxAttempts(rp.numRetries())
                        .build()
                        .execute(r -> perform(request)))
                .orElseGet(() -> perform(request));
    }

    private Result<ModelServerResponse, DataSourceClient.DataSourceClientPrototypeError> perform(ModelServerRequest request) {
        return responseDeser.deserialize(
                modelServerRestClient.post()
                        .uri(request.getUrl(), request.getPath())
                        .body(request.getContent())
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .headers(h -> h.putAll(request.getHeaders()))
                        .retrieve()
                        .body(String.class));
    }

}
