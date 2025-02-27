package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hayden.proto.prototype.datasource.data.inputs.request.BodyContractProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerChatRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerRerankRequest;
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

import java.util.List;
import java.util.Map;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
public class ModelServerRerankAiClient {

    @Builder
    public record RerankDocument(String text, @JsonProperty("doc_id") String docId) {}

    @Builder
    public record RerankResultDocument(String text, int rank, @JsonProperty("doc_id") String docId, @JsonProperty("document_type") String documentType) {}

    @Builder
    public record ModelServerRerankBody(@JsonProperty("rerank_body") RerankBody rerankBody) implements BodyContractProto {

//        public ModelServerRerankBody(RerankBody rerankBody) {
//            this(null, rerankBody);
//        }

//        public ModelServerRerankBody withModel(String model) {
//            return new ModelServerRerankBody(model, rerankBody);
//        }
    }

    @Builder
    public record RerankBody(String query, List<RerankDocument> docs) implements BodyContractProto {}

    @Builder
    public record RerankResult(@JsonProperty("ranked_results") Map<Integer, RerankResultDocument> rerankedResults, String query) {}

    @Builder
    public record ModelServerRerankResponse(RerankResult validationResult) {}

    @Autowired(required = false)
    private RestClient modelServerRestClient = RestClient.builder().build();

    public Result<ModelServerRerankResponse, DataSourceClient.Err> send(ModelServerRerankRequest request) {
        return Result.ok(
                new ModelServerRerankResponse(
                        modelServerRestClient.post()
                                .uri(request.getUrl(), request.getPath())
                                .body(request.getContent())
                                .headers(h -> h.putAll(request.getHeaders()))
                                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .retrieve()
                                .body(RerankResult.class)));
    }

}
