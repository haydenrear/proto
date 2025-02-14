package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hayden.proto.prototype.datasource.data.inputs.request.BodyContractProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerChatRequest;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.proto.prototyped.sources.client.Response;
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.utilitymodule.result.Result;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
@DataClient(proto = ValidationModelServerContractProto.class)
public class ModelServerValidationAiClient {

    @Builder
    public record ValidationAiClientResponseBody() implements BodyContractProto {}

    @Body(proto = ValidationAiClientResponseBody.class)
    @Builder
    public record ValidationResult(@JsonProperty("validation_score") float validationScore) {}

    @Builder
    @Response(proto = ModelServerResponseContract.class)
    public record ModelServerValidationResponse(ValidationResult validationResult) {}

    @Autowired(required = false)
    private RestClient modelServerRestClient = RestClient.builder().build();

    @RequestResponse(requestSource = ModelServerChatRequest.class, responseSource = ModelServerValidationResponse.class)
    public Result<ModelServerValidationResponse, DataSourceClient.Err> send(ModelServerChatRequest request) {
        return Result.ok(
                new ModelServerValidationResponse(
                        modelServerRestClient.post()
                                .uri(request.getUrl(), request.getPath())
                                .body(request.getContent())
                                .headers(h -> h.putAll(request.getHeaders()))
                                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .retrieve()
                                .body(ValidationResult.class)));
    }

}
