package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import com.hayden.proto.prototype.datasource.data.inputs.request.RetryProto;
import com.hayden.proto.prototyped.sources.client.RequestSourceDesc;
import com.hayden.proto.prototyped.sources.data.inputs.Url;
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.proto.prototyped.sources.data.inputs.request.Path;
import com.hayden.proto.prototyped.sources.requestresponse.Headers;
import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.datasource.data.wire.StaticWireProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.StaticApiRequestContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.WireTypeRequestContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.data.ModelServerRecordProto;
import com.hayden.proto.prototyped.sources.retry.Retry;
import com.hayden.utilitymodule.ctx.PrototypeScope;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
@PrototypeScope
@RequestSourceDesc(proto = ModelServerRequest.ModelServerApiRequestContractProto.class)
@Setter
public class ModelServerRequest {

    @Body(proto = ModelServerRecordProto.class)
    public record ModelServerBody(String prompt, ModelServerRequestType requestType) {
        public ModelServerBody(String prompt) {
            this(prompt, null);
        }

        public ModelServerBody withRequestType(ModelServerRequestType requestType) {
            return new ModelServerBody(this.prompt, requestType);
        }
    }

    public enum ModelServerRequestType {
        EMBEDDING, TOOLSET, CODEGEN, INITIAL_CODE
    }

    ModelServerBody content;

    @Headers(proto = ModelServerAiRequestHeaders.class)
    HttpHeaders headers;
    @Url
    String url;
    @Path
    String path;
    @Retry(proto = RetryProto.class)
    RetryParameters retryParameters;


    public ModelServerRequest(ModelServerBody content) {
        this.content = content;
    }

    public record RetryParameters(int numRetries) {}

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
