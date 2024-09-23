package com.hayden.proto.datasources.ai.huggingface.request;

import com.hayden.proto.datasource.inputs.request.Body;
import com.hayden.proto.datasource.inputs.request.api.ApiRequest;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.cardinality.Many;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.wiretype.StaticWireProto;
import com.hayden.proto.datasource_proto.inputs.request.StaticApiRequestContractProto;
import com.hayden.proto.datasource_proto.inputs.request.WireTypeRequestContractProto;
import com.hayden.proto.datasource_proto.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.datasources.ai.huggingface.data.ModelServerRecordProto;
import com.hayden.proto.ty.Prototyped;
import com.hayden.shared.config.PrototypeScope;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@PrototypeScope
public class ModelServerRequest implements ApiRequest {

    ModelServerBody content;

    @Autowired
    ModelServerApiRequestContractProto requestConstructProtoAny;

    public ModelServerRequest(ModelServerBody content) {
        this(content, null);
    }

    // TODO: property source will provide the contract proto
    public ModelServerRequest(ModelServerBody content,
                              ModelServerApiRequestContractProto requestConstructProtoAny) {
        this.content = content;
        this.requestConstructProtoAny = requestConstructProtoAny;
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

    public record ModelServerBody(String prompt, ModelServerRecordProto proto)
            implements Body<ModelServerBody> {

        public ModelServerBody(String prompt) {
            this(prompt, new ModelServerRecordProto());
        }

        @Override
        public ModelServerBody wrapped() {
            return this;
        }
    }

    public HttpHeaders headers() {
        return this.requestConstructProtoAny.requestConstructProto()
                .retrieveMany(AiRequestConstructProto.AiRestContract.AiRequestHeader.class)
                .stream()
                .flatMap(a -> a.value().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (k1, k2) -> {throw new RuntimeException("Either one header or the other! %s vs %s.".formatted(k1, k2));},
                        HttpHeaders::new
                ));
    }

    @Override
    public ModelServerApiRequestContractProto proto() {
        return this.requestConstructProtoAny;
    }

    public ModelServerBody content() {
        return content;
    }

}
