package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RetryProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.StaticApiRequestContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.WireTypeRequestContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.prototype.datasource.data.wire.StaticWireProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.data.ModelServerRecordProto;
import com.hayden.proto.prototyped.sources.client.RequestSourceDesc;
import com.hayden.proto.prototyped.sources.data.inputs.Url;
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.proto.prototyped.sources.data.inputs.request.Path;
import com.hayden.proto.prototyped.sources.requestresponse.Headers;
import com.hayden.proto.prototyped.sources.retry.Retry;
import com.hayden.utilitymodule.ctx.PrototypeScope;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
@PrototypeScope
@RequestSourceDesc(proto = ModelServerEmbeddingRequest.ModelServerApiRequestContractProto.class)
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelServerEmbeddingRequest implements WithRetryParams {

    @Override
    public void addExceptionMessage(String exceptionMessage) {
        this.content = this.content.withException(exceptionMessage);
    }


    @Body(proto = ModelServerRecordProto.class)
    public record ModelServerBody<T>(
            @JsonProperty("to_embed") T toEmbed,
            String model,
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            @JsonProperty(value = "Retrying request. Saw this message last time - please try again:") String exceptionMessage) {
        public ModelServerBody(T toEmbed) {
            this(toEmbed, null, null);
        }

        public ModelServerBody withModel(String model) {
            return new ModelServerBody(toEmbed, model, exceptionMessage);
        }

        public ModelServerBody withException(String exceptionMessage) {
            return new ModelServerBody(toEmbed, model, exceptionMessage);
        }


        public ModelServerBody<T> withNewContent(T t) {
            return new ModelServerBody<>(t, model, exceptionMessage);
        }
    }

    ModelServerBody content;

    @Headers(proto = ModelServerAiRequestHeaders.class)
    HttpHeaders headers;
    @Url
    String url;
    @Path
    String path;
    @Retry(proto = RetryProto.class)
    @Getter
    RetryParameters retryParameters;

    int maxLength;

    public <T> ModelServerEmbeddingRequest withNewBody(ModelServerBody<T> body) {
        return new ModelServerEmbeddingRequest(body, headers, url, path, retryParameters, maxLength);
    }


    public ModelServerEmbeddingRequest(ModelServerBody content) {
        this.content = content;
    }

    public record ModelServerAiRequestHeaders()
            implements AiRequestConstructProto.AiRestContract.AiRequestHeader {

        @Override
        public KeyContractProto key() {
            return null;
        }


        @Override
        public ValueContractProto.PermittingKeyValueContract permittingKeyValueContract() {
            return null;
        }
    }

    public record ModelServerApiRequestContractProto(Plural<AiRequestConstructProto> requestConstructProto)
            implements StaticApiRequestContractProto {

        @Override
        public Any<AiRequestConstructProto> requestContracts() {
            return () -> requestConstructProto.pluralize().toArray(AiRequestConstructProto[]::new);
        }

        @Override
        public WireTypeRequestContractProto wire() {
            return () -> StaticWireProto.EMPTY;
        }
    }

}
