package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.proto.prototyped.sources.client.Response;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerRequest;
import com.hayden.utilitymodule.result.Result;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
@DataClient(proto = CodingModelServerContractProto.class)
public class ModelServerCodingAiClient {

    @Builder
    public record CodeResult(String data) {}

    @Builder
    @Response(proto = ModelServerResponseContract.class)
    public record ModelServerCodeResponse(CodeResult codeResult) { }

    @Autowired(required = false)
    private RestClient modelServerRestClient = RestClient.builder().build();

    @SneakyThrows
    @RequestResponse(requestSource = ModelServerRequest.class, responseSource = ModelServerCodeResponse.class)
    public Result<ModelServerCodeResponse, DataSourceClient.DataSourceClientPrototypeError> send(ModelServerRequest request) {
        return Result.ok(
                new ModelServerCodeResponse(
                        modelServerRestClient.post()
                                .uri(request.getUrl(), request.getPath())
                                .body(request.getContent())
                                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .headers(h -> h.putAll(request.getHeaders()))
                                .retrieve()
                                .body(CodeResult.class)));
    }

}
