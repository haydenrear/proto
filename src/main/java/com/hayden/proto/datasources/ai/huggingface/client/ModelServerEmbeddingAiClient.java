package com.hayden.proto.datasources.ai.huggingface.client;

import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.data.response.ResponseConstructContractProto;
import com.hayden.proto.datasources.ai.huggingface.data.ModelServerRecordProto;
import com.hayden.proto.datasources.ai.huggingface.request.ModelServerRequest;
import com.hayden.proto.proto.Prototype;
import com.hayden.utilitymodule.result.Result;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.*;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
public class ModelServerEmbeddingAiClient implements
        ModelServerClient<ModelServerEmbeddingAiClient.ModelServerEmbeddingResponse> {

    private final ModelServerContractProto clientContractProto;

    private RestClient template;

    @PostConstruct
    public void setup() {
        template = RestClient.builder()
                .baseUrl(clientContractProto.url())
                .build();
    }

    public record EmbeddingResult(float[] data) {}

    @Builder
    public record ModelServerEmbeddingResponse(ModelServerEmbeddingAiClient self, EmbeddingResult embedding)
            implements DataRecordResponseRecord {

        @Override
        public ModelServerResponseContract proto() {
            return self.clientContractProto.response();
        }

    }

    @Override
    public Result<ModelServerEmbeddingResponse, DataSourceClientPrototypeError> send(ModelServerRequest request) {
        // TODO: pull this validation into a cross-cutting concern
//        return request.proto().is(request)
//                .mapError(pErr -> new DataSourceClientPrototypeError(pErr.getMessage()))
//                .filterRes(pErr -> pErr.map(Boolean::booleanValue).orElse(false))
//                .flatMapResult(b ->
                        return Result.ok(
                        new ModelServerEmbeddingResponse(
                                this,
                                template.post()
                                        .body(request.content())
//                                      .headers(h -> h.putAll(request.headers()))
                                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .retrieve()
                                        .body(EmbeddingResult.class)
                        ));
//                        ));
    }

    @Override
    public ModelServerContractProto proto() {
        return clientContractProto;
    }

    @Override
    public <PR extends Prototype> Optional<PR> retrieve(Class<PR> prototype) {
        return Arrays.stream(this.proto().requestContracts().ofMany())
                .filter(r -> r.getClass().equals(prototype))
                .map(r -> (PR) r)
                .findAny();
    }
}
