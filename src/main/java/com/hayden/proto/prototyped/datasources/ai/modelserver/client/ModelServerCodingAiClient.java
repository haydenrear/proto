package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes.DelegatingModelServerResponseDeser;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes.ModelServerResponseDeser;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerChatRequest;
import com.hayden.utilitymodule.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
@DataClient(proto = CodingModelServerContractProto.class)
public class ModelServerCodingAiClient implements RetryableClient<ModelServerChatRequest, ModelServerResponse> {


    public record CodeResult<T>(T data) {}

    @Autowired(required = false)
    private RestClient modelServerRestClient = RestClient.builder().build();

    @Setter
    private DelegatingModelServerResponseDeser responseDeser;

    public Result<ModelServerResponse, DataSourceClient.Err> send(ModelServerChatRequest request) {
        return callWithRetry(request);
    }

    @SneakyThrows
    @RequestResponse(requestSource = ModelServerChatRequest.class, responseSource = ModelServerResponse.class)
    public Result<ModelServerResponse, DataSourceClient.Err> doSend(ModelServerChatRequest request) {
        return perform(request);
    }

    private Result<ModelServerResponse, DataSourceClient.Err> perform(ModelServerChatRequest request) {
        Result<ModelServerResponse, DataSourceClient.Err> desered =  responseDeser.deserialize(
                modelServerRestClient.post()
                        .uri(request.getUrl(), request.getPath())
                        .body(request.getContent())
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .headers(h -> h.putAll(request.getHeaders()))
                        .retrieve()
                        .body(String.class))
                .map(mr -> mr);
        return desered;

    }

}
