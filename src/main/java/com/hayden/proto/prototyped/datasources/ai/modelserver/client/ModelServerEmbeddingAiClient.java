package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerEmbeddingRequest;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.proto.prototyped.sources.client.Response;
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.proto.prototype.datasource.data.inputs.request.BodyContractProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerChatRequest;
import com.hayden.utilitymodule.result.Result;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
@DataClient(proto = EmbeddingModelServerContractProto.class)
public class ModelServerEmbeddingAiClient {

    @Builder
    public record EmbeddingAiClientResponseBody() implements BodyContractProto {}

    @Body(proto = EmbeddingAiClientResponseBody.class)
    @Builder
    public record EmbeddingResult(float[] embedding) {}

    @Builder
    @Response(proto = ModelServerResponseContract.class)
    public record ModelServerEmbeddingResponse(EmbeddingResult embedding) {}

    @Autowired(required = false)
    private RestClient modelServerRestClient = RestClient.builder().build();

    @RequestResponse(requestSource = ModelServerEmbeddingRequest.class, responseSource = ModelServerEmbeddingResponse.class)
    public Result<ModelServerEmbeddingResponse, DataSourceClient.DataSourceClientPrototypeError> send(ModelServerEmbeddingRequest request) {
        return Result.ok(
                new ModelServerEmbeddingResponse(
                        modelServerRestClient.post()
                                .uri(request.getUrl(), request.getPath())
                                .body(request.getContent())
                                .headers(h -> Optional.ofNullable(h)
                                        .flatMap(toAdd -> Optional.ofNullable(request.getHeaders()))
                                        .ifPresent(h::putAll))
                                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .retrieve()
                                .body(EmbeddingResult.class)));
    }

}
