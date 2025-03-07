package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

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
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.proto.prototyped.sources.retry.Retry;
import com.hayden.utilitymodule.ctx.PrototypeScope;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Slf4j
@Component
@PrototypeScope
@RequestSourceDesc(proto = ModelContextProtocolRequest.ModelServerApiRequestContractProto.class)
public class ModelContextProtocolRequest implements WithRetryParams {

    @Override
    public void addExceptionMessage(String exceptionMessage) {
        this.content = this.content.withException(exceptionMessage);
    }

    @Body(proto = ModelServerRecordProto.class)
    public record ContextRequestBatch(List<ModelContextProtocolContextRequest> prompt,
                                      @JsonProperty(value = "Retrying request. Saw this message last time - please try again:") String exceptionMessage) {
        public ContextRequestBatch(List<ModelContextProtocolContextRequest> prompt) {
            this(prompt, null);
        }

        public ContextRequestBatch withException(String message) {
            return new ContextRequestBatch(prompt, message);
        }
    }

    ContextRequestBatch content;

    @Retry(proto = RetryProto.class)
    @Getter
    RetryParameters retryParameters;

    public ModelContextProtocolRequest(ContextRequestBatch content) {
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
