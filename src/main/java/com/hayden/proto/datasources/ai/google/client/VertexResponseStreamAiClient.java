package com.hayden.proto.datasources.ai.google.client;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseStream;
import com.google.common.collect.Sets;
import com.hayden.proto.datasource_proto.cardinality.Many;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.response.DataRecordResponseConstructContractProto;
import com.hayden.proto.datasource_proto.data.response.ResponseConstructContractProto;
import com.hayden.proto.datasources.ai.google.data.VertexDataRecordProto;
import com.hayden.proto.datasources.ai.google.request.VertexRequest;
import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.proto.datasource_proto.data.wiretype.StreamingWireProto;
import com.hayden.proto.datasource_proto.inputs.request.RequestConstructContractProto;
import com.hayden.proto.datasource_proto.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.proto.Prototype;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.ErrorCollect;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class VertexResponseStreamAiClient implements
        VertexClient<StreamingWireProto.PermittingResponseStream<VertexDataRecordProto>, VertexResponseStreamAiClient.VertexStreamingResponseRecord> {

    private final Map<String, VertexAiSession> sessions = new ConcurrentHashMap<>();

    private final VertexDataSourceClientContractProto clientContractProto;

    public interface VertexResponseStreamDataRecord extends DataRecordResponseContract {
        @Override
        Any<ResponseConstructContractProto> responseContracts();
    }

    public record VertexStreamingResponseRecord(ResponseStream<GenerateContentResponse> contentResponses,
                                                @Nullable VertexSessionKey sessionKey,
                                                VertexResponseStreamAiClient self)
            implements DataRecordResponseRecord {

        @Override
        public VertexResponseStreamDataRecord proto() {
            return self.clientContractProto.response;
        }
    }

    record VertexAiSession(ChatSession chatSession, VertexAI vertexAI, VertexSessionKey sessionKey) {}


    @SneakyThrows
    @Override
    public Result<VertexStreamingResponseRecord, DataSourceClientPrototypeError> send(VertexRequest request) {
        Set<RequestConstructContractProto> failed = Arrays.stream(this.proto().requestContracts().ofMany())
                .filter(Predicate.not(r -> r.is(request)))
                .collect(Collectors.toSet());

        if (!failed.isEmpty()) {
            return Result.err(new DataSourceClientPrototypeError(Sets.newHashSet(
                    new ErrorCollect.StandardError("No matching prototype for %s".formatted(request.getClass().getSimpleName())))));
        }

        var session = Optional.ofNullable(request.vertexSessionKey())
                .map(VertexSessionKey::wrapped)
                .map(sessions::get)
                .orElseGet(() -> createGetSessionFromUserSessionId(request));

        return Result.ok(new VertexStreamingResponseRecord(session.chatSession().sendMessageStream(request.content().wrapped()), session.sessionKey(), this));
    }

    @SneakyThrows
    private @NotNull VertexAiSession createGetSessionFromUserSessionId(VertexRequest request) {
        return sessions.compute(request.userSessionId(), (key, prev) -> Optional.ofNullable(prev)
                .orElseGet(() -> {
                    VertexAI vertexAI = new VertexAI(request.vertexProjectId().wrapped(), request.vertexLocation().wrapped());
                    GenerativeModel model = new GenerativeModel(request.vertexModelName().wrapped(), vertexAI);
                    String sessionKey = Optional.ofNullable(request.userSessionId()).orElse(UUID.randomUUID().toString());
                    return new VertexAiSession(model.startChat(), vertexAI, new VertexSessionKey(sessionKey));
                }));
    }

    @Builder
    public record VertexDataSourceClientContractProto(
            Any<AiRequestConstructProto>  requestConstructs,
            VertexResponseStreamDataRecord response
    ) implements DataSourceClientContractProto {

        public VertexDataSourceClientContractProto(
                int contextLength,
                String modelLocation,
                String projectId,
                String modelName
        ) {
            this(
                    () -> new AiRequestConstructProto[]{
                            new AiRequestConstructProto.AiContextLengthContractProto.PermitsNumberContractProto(contextLength),
                            new AiRequestConstructProto.AiModelNameProto.PermitsStringContractProto(modelName),
                            new AiRequestConstructProto.AiProjectContractProtoProto.PermitsStringContractProto(projectId),
                            new AiRequestConstructProto.AiModelLocationContractProto.PermitsStringContractProto(modelLocation)
                    },
                    responseContracts(new VertexDataRecordProto(), new ResponseConstructContractProto[]{})
            );
        }

        public VertexDataSourceClientContractProto(
                int contextLength,
                String modelLocation,
                String projectId,
                String modelName,
                VertexDataRecordProto response,
                ResponseConstructContractProto[] responseConstructContractProtos
        ) {
            this(
                    () -> new AiRequestConstructProto[]{
                            new AiRequestConstructProto.AiContextLengthContractProto.PermitsNumberContractProto(contextLength),
                            new AiRequestConstructProto.AiModelNameProto.PermitsStringContractProto(modelName),
                            new AiRequestConstructProto.AiProjectContractProtoProto.PermitsStringContractProto(projectId),
                            new AiRequestConstructProto.AiModelLocationContractProto.PermitsStringContractProto(modelLocation)
                    },
                    responseContracts(response, responseConstructContractProtos)
            );
        }

        public static VertexResponseStreamDataRecord responseContracts(VertexDataRecordProto response,
                                                                       ResponseConstructContractProto[] responseConstructContractProtos) {
            DataRecordResponseConstructContractProto dataRecordResponseConstructContractProto = () -> (Many<DataRecordContractProto>) () -> response;
            var t = Arrays.copyOf(responseConstructContractProtos, responseConstructContractProtos.length + 1);
            t[responseConstructContractProtos.length] = dataRecordResponseConstructContractProto;
            return () -> (Any<ResponseConstructContractProto>) () -> t;
        }

        @Override
        public Any<AiRequestConstructProto> requestContracts() {
            return this.requestConstructs;
        }

        @Override
        public Any<ResponseConstructContractProto> responseContracts() {
            return response.responseContracts();
        }

    }

    @Override
    public VertexDataSourceClientContractProto proto() {
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
