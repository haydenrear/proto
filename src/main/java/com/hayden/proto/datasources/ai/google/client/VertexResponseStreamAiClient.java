package com.hayden.proto.datasources.ai.google.client;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseStream;
import com.google.common.collect.Sets;
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

    private final int contextLength;
    private final String projectId;
    private final String modelName;

    public record VertexStreamingResponseRecord(ResponseStream<GenerateContentResponse> contentResponses, @Nullable VertexSessionKey sessionKey)
            implements DataRecordResponseRecord<StreamingWireProto.PermittingResponseStream<VertexDataRecordProto>> {

        @Override
        public DataRecordResponseContract<StreamingWireProto.PermittingResponseStream<VertexDataRecordProto>> proto() {
            return null;
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
                .orElseGet(() -> createSession(request));

        return Result.ok(new VertexStreamingResponseRecord(session.chatSession().sendMessageStream(request.content().wrapped()), session.sessionKey()));
    }

    @SneakyThrows
    private @NotNull VertexAiSession createSession(VertexRequest request) {
        VertexAI vertexAI = new VertexAI(request.projectId().wrapped(), request.location().wrapped());
        GenerativeModel model = new GenerativeModel("gemini-pro", vertexAI);
        String sessionKey = Optional.ofNullable(request.userSessionId()).orElse(UUID.randomUUID().toString());
        VertexAiSession session = new VertexAiSession(model.startChat(), vertexAI, new VertexSessionKey(sessionKey));
        sessions.put(sessionKey, session);
        return session;
    }

    public record VertexDataSourceClientContractProto(
            int contextLength,
            String modelName,
            String projectId
    ) implements DataSourceClientContractProto {
        @Override
        public Any<AiRequestConstructProto> requestContracts() {
            return () -> new AiRequestConstructProto[] {
                    new AiRequestConstructProto.ContextLength.PermitsNumberContractProto(contextLength),
                    new AiRequestConstructProto.AiModelNameProto.PermitsStringContractProto(modelName),
                    new AiRequestConstructProto.AiProjectContractProtoProto.PermitsStringContractProto(projectId)
            };
        }

        @Override
        public Any<DataRecordContractProto> responseContracts() {
            return null;
        }
    }

    @Override
    public VertexDataSourceClientContractProto proto() {
        return new VertexDataSourceClientContractProto(contextLength, modelName, projectId);
    }

    @Override
    public <PR extends Prototype> Optional<PR> retrieve(Class<PR> prototype) {
        return Arrays.stream(this.proto().requestContracts().ofMany())
                .filter(r -> r.getClass().equals(prototype))
                .map(r -> (PR) r)
                .findAny();
    }
}
