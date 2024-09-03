package com.hayden.proto.datasources.ai.google.request;

import com.google.cloud.vertexai.api.Content;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.inputs.request.StaticApiRequestContractProto;
import com.hayden.proto.datasource_proto.inputs.request.WireTypeRequestContractProto;
import com.hayden.proto.datasources.ai.google.client.VertexClient;
import com.hayden.proto.datasources.ai.google.data.VertexDataRecordProto;
import com.hayden.proto.datasource.inputs.request.api.ApiRequest;
import com.hayden.proto.datasource_proto.data.wiretype.StaticWireProto;
import com.hayden.proto.datasource_proto.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.proto.Prototype;
import com.hayden.proto.ty.CompositePrototyped;
import com.hayden.proto.ty.Prototyped;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public record VertexRequest(Plural<AiRequestConstructProto> requestConstructProtoAny,
                            VertexContent content,
                            @Nullable String userSessionId,
                            @Nullable VertexClient.VertexSessionKey vertexSessionKey)
        implements ApiRequest {


    public VertexRequest(VertexContent content, Plural<AiRequestConstructProto> requestConstructProtoAny) {
        this(
                content,
                requestConstructProtoAny,
                null,
                null
        );
    }

    public VertexRequest(VertexContent content,
                         Plural<AiRequestConstructProto> requestConstructProtoAny,
                         @Nullable VertexClient.VertexSessionKey vertexSessionKey,
                         @Nullable String userSessionId) {
        this(
                requestConstructProtoAny,
                content,
                userSessionId,
                vertexSessionKey
        );
    }

    public VertexProjectId vertexProjectId() {
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiProjectProtoValue.PermitsStringContractProto.class)
                .map(p -> p.value().value())
                .map(VertexProjectId::new)
                .orElseThrow();
    }

    public VertexModelName vertexModelName() {
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiProjectProtoValue.PermitsStringContractProto.class)
                .map(p -> p.value().value())
                .map(VertexModelName::new)
                .orElseThrow();
    }

    public VertexLocation vertexLocation() {
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiModelNameProtoValue.PermitsStringContractProto.class)
                .map(p -> p.value().value())
                .map(VertexLocation::new)
                .orElseThrow();
    }

    public VertexContextLength vertexContextLength() {
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiContextLengthProtoValue.PermitsNumberContractProto.class)
                .map(p -> p.value().value())
                .map(VertexContextLength::new)
                .orElseThrow();
    }

    public record VertexApiRequestContract(Plural<AiRequestConstructProto> requestConstructProtoAny) implements StaticApiRequestContractProto {

        @Override
        public <PR extends Prototyped<P>, P extends Prototype> Optional<PR> retrieve(P toGet, CompositePrototyped<P> retrieveFrom) {
            if (retrieveFrom instanceof VertexRequest v) {
                return Optional.ofNullable(switch(toGet) {
                    case VertexDataRecordProto ignored -> (PR) v.content;
                    case AiRequestConstructProto.AiContextLengthProtoValue ignored -> (PR) v.vertexContextLength();
                    case AiRequestConstructProto.AiProjectProtoValue ignored -> (PR) v.vertexProjectId();
                    case AiRequestConstructProto.AiModelNameProtoValue ignored -> (PR) v.vertexModelName();
                    case AiRequestConstructProto.AiModelLocationProtoValue ignored -> (PR) v.vertexLocation();
                    default -> {
                        log.error("Error!");
                        yield null;
                    }
                });
            }

            return Optional.empty();
        }

        @Override
        public Any<AiRequestConstructProto> requestContracts() {
            return () -> requestConstructProtoAny.pluralize().toArray(AiRequestConstructProto[]::new);
        }

        @Override
        public WireTypeRequestContractProto wire() {
            return () -> new StaticWireProto(){};
        }

    }

    public record VertexContent(@Nullable Content wrapped) implements Prototyped<VertexDataRecordProto> {

        @Override
        public VertexDataRecordProto proto() {
            return new VertexDataRecordProto();
        }
    }

    public record VertexContextLength(Integer wrapped) implements Prototyped<AiRequestConstructProto.AiContextLengthProtoValue> {

        @Override
        public AiRequestConstructProto.AiContextLengthProtoValue proto() {
            return new AiRequestConstructProto.AiContextLengthProtoValue.PermitsNumberContractProto(wrapped);
        }
    }

    public record VertexModelName(String wrapped) implements Prototyped<AiRequestConstructProto.AiModelNameProtoValue> {

        @Override
        public AiRequestConstructProto.AiModelNameProtoValue proto() {
            return new AiRequestConstructProto.AiModelNameProtoValue.PermitsStringContractProto(wrapped);
        }
    }

    public record VertexProjectId(String wrapped) implements Prototyped<AiRequestConstructProto.AiProjectProtoValue> {

        @Override
        public AiRequestConstructProto.AiProjectProtoValue proto() {
            return new AiRequestConstructProto.AiProjectProtoValue.PermitsStringContractProto(wrapped);
        }
    }

    public record VertexLocation(String wrapped)
            implements Prototyped<AiRequestConstructProto.AiModelNameProtoValue> {

        @Override
        public AiRequestConstructProto.AiModelNameProtoValue proto() {
            return new AiRequestConstructProto.AiModelNameProtoValue.PermitsStringContractProto(wrapped);
        }
    }

    @Override
    public VertexApiRequestContract proto() {
        return new VertexApiRequestContract(this.requestConstructProtoAny);
    }


}
