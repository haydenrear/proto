package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerEmbeddingRequest;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.proto.prototyped.sources.client.Response;
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.proto.prototype.datasource.data.inputs.request.BodyContractProto;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.SingleError;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
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
    @Autowired
    private ObjectMapper objectMapper;

    @RequestResponse(requestSource = ModelServerEmbeddingRequest.class, responseSource = ModelServerEmbeddingResponse.class)
    public Result<ModelServerEmbeddingResponse, DataSourceClient.Err> send(ModelServerEmbeddingRequest request) {
        try {
            ModelServerEmbeddingRequest.ModelServerBody thisBody = request.getContent().withNewContent(objectMapper.writeValueAsString(request.getContent().toEmbed()));
            String body = modelServerRestClient.post()
                    .uri(request.getUrl())
                    .body(thisBody)
                    .headers(h -> Optional.ofNullable(h)
                            .flatMap(toAdd -> Optional.ofNullable(request.getHeaders()))
                            .ifPresent(h::putAll))
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .body(String.class);
            var b = objectMapper.readValue(body, EmbeddingResult.class);
            return Result.ok(
                    new ModelServerEmbeddingResponse(b));
        } catch (JsonProcessingException | IllegalArgumentException |
                 HttpServerErrorException.InternalServerError e) {
            log.error(e.getMessage(), e);
            return Result.err(new DataSourceClient.Err(SingleError.parseStackTraceToString(e)));
        }
    }

}
