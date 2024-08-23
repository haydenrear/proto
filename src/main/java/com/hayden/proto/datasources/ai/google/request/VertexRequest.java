package com.hayden.proto.datasources.ai.google.request;

import com.google.cloud.vertexai.api.Content;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.inputs.request.StaticApiRequestContractProto;
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
        implements ApiRequest<VertexRequest.VertexContent, StaticWireProto> {


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
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiProjectContractProtoProto.PermitsStringContractProto.class)
                .map(p -> p.permitting().value())
                .map(VertexProjectId::new)
                .orElseThrow();
    }

    public VertexModelName vertexModelName() {
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiProjectContractProtoProto.PermitsStringContractProto.class)
                .map(p -> p.permitting().value())
                .map(VertexModelName::new)
                .orElseThrow();
    }

    public VertexLocation vertexLocation() {
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiModelNameProto.PermitsStringContractProto.class)
                .map(p -> p.permitting().value())
                .map(VertexLocation::new)
                .orElseThrow();
    }

    public VertexContextLength vertexContextLength() {
        return requestConstructProtoAny.retrieve(AiRequestConstructProto.AiContextLengthContractProto.PermitsNumberContractProto.class)
                .map(p -> p.permitting().value())
                .map(VertexContextLength::new)
                .orElseThrow();
    }

    public record VertexApiRequestContract(Plural<AiRequestConstructProto> requestConstructProtoAny) implements StaticApiRequestContractProto {

        @Override
        public <PR extends Prototyped<P, U>, P extends Prototype, U> Optional<PR> retrieve(P toGet, CompositePrototyped<P> retrieveFrom) {
            if (retrieveFrom instanceof VertexRequest v) {
                return Optional.ofNullable(switch(toGet) {
                    case VertexDataRecordProto ignored -> (PR) v.content;
                    case AiRequestConstructProto.AiContextLengthContractProto ignored -> (PR) v.vertexContextLength();
                    case AiRequestConstructProto.AiProjectContractProtoProto ignored -> (PR) v.vertexProjectId();
                    case AiRequestConstructProto.AiModelNameProto ignored -> (PR) v.vertexModelName();
                    case AiRequestConstructProto.AiModelLocationContractProto ignored -> (PR) v.vertexLocation();
                    default -> {
                        log.error("Error!");
                        yield null;
                    }
                });
            }

            return Optional.empty();
        }

        @Override
        public Any<AiRequestConstructProto> requestItems() {
            return () -> requestConstructProtoAny.pluralize().toArray(AiRequestConstructProto[]::new);
        }

        @Override
        public StaticWireProto wireContract() {
            return new StaticWireProto() {};
        }

    }

    public record VertexContent(@Nullable Content wrapped) implements Prototyped<VertexDataRecordProto, Content> {

        @Override
        public VertexDataRecordProto proto() {
            return new VertexDataRecordProto();
        }
    }

    public record VertexContextLength(Integer wrapped) implements Prototyped<AiRequestConstructProto.AiContextLengthContractProto, Integer> {

        @Override
        public AiRequestConstructProto.AiContextLengthContractProto proto() {
            return new AiRequestConstructProto.AiContextLengthContractProto.PermitsNumberContractProto(() -> wrapped);
        }
    }

    public record VertexModelName(String wrapped) implements Prototyped<AiRequestConstructProto.AiModelNameProto, String> {

        @Override
        public AiRequestConstructProto.AiModelNameProto proto() {
            return new AiRequestConstructProto.AiContextLengthContractProto.AiModelNameProto.PermitsStringContractProto(() -> wrapped);
        }
    }

    public record VertexProjectId(String wrapped) implements Prototyped<AiRequestConstructProto.AiProjectContractProtoProto, String> {

        @Override
        public AiRequestConstructProto.AiProjectContractProtoProto proto() {
            return new AiRequestConstructProto.AiProjectContractProtoProto.PermitsStringContractProto(() -> wrapped);
        }
    }

    public record VertexLocation(String wrapped) implements Prototyped<AiRequestConstructProto.AiModelNameProto, String> {

        @Override
        public AiRequestConstructProto.AiModelNameProto proto() {
            return new AiRequestConstructProto.AiModelNameProto.PermitsStringContractProto(() -> wrapped);
        }
    }

    @Override
    public VertexApiRequestContract proto() {
        return new VertexApiRequestContract(this.requestConstructProtoAny);
    }


}
