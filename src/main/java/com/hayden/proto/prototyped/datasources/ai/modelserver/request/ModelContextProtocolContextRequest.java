package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hayden.shared.models.derivative.IComponentDerivative;
import io.modelcontextprotocol.kotlin.sdk.JSONRPCRequest;
import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.ai.mcp.client.transport.ServerParameters;
import org.springframework.ai.mcp.spec.McpSchema;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public record ModelContextProtocolContextRequest(MpcToolsetRequest toolsetRequest,
                                                 MpcServerDescriptor serverParams){


    public Optional<MpcServerDescriptor> serverDescriptor() {
        return Optional.ofNullable(serverParams);
    }

    public Optional<MpcToolsetRequest> toCallToolRequest() {
        return Optional.ofNullable(toolsetRequest);
    }

    @Builder
    @JsonDeserialize(using = MpcServerDescriptor.ToolsetDeser.class)
    public record MpcServerDescriptor(@JsonValue ServerParameters serverParameters) {

        public static class ToolsetDeser extends JsonDeserializer<MpcServerDescriptor> {

            private static final ObjectMapper om = new ObjectMapper();

            @Override
            public MpcServerDescriptor deserialize(JsonParser jsonParser,
                                                   DeserializationContext deserializationContext) throws IOException {
                var n = jsonParser.readValueAsTree();
                var env = n.get("env");
                var cmd = n.get("command");
                var args = n.get("args");
                Map<String, String> envs = env == null ? new HashMap<>() : om.readValue(env.traverse(om), new TypeReference<>() {});
                return new MpcServerDescriptor(
                        ServerParameters
                                .builder(cmd == null ? "" : om.readValue(cmd.traverse(om), String.class))
                                .env(envs)
                                .args(args == null ? new String[] {} : om.readValue(args.traverse(om), new TypeReference<>() {}))
                                .build(),
                        envs);
            }
        }

        public MpcServerDescriptor(ServerParameters serverParameters) {
            this.serverParameters = serverParameters;
            this.serverParameters.getEnv().clear();
        }

        public MpcServerDescriptor(ServerParameters serverParameters, Map<String, String> env) {
            this(serverParameters);
            this.serverParameters.getEnv().clear();
            this.serverParameters.getEnv().putAll(env);
        }
    }

    @JsonDeserialize(using = MpcToolsetRequest.ToolsetDeser.class)
    public sealed interface MpcToolsetRequest {


        class ToolsetDeser extends JsonDeserializer<MpcToolsetRequest> {

            private static final ObjectMapper om = new ObjectMapper();

            @Override
            public MpcToolsetRequest deserialize(JsonParser jsonParser,
                                                 DeserializationContext deserializationContext) throws IOException {
                return new PassthroughToolsetRequest(om.readValue(jsonParser.readValueAsTree().traverse(om), McpSchema.JSONRPCRequest.class));
            }
        }

        @JsonValue
        McpSchema.JSONRPCRequest toJSONRPCRequest();

        @Builder
        record SerMpcToolsetRequest(String jsonRpc, String method, String id, Map<String, MpcParam> params) implements MpcToolsetRequest {

            public SerMpcToolsetRequest(Map<String, MpcParam> params) {
                this("2.0", "tools/call", UUID.randomUUID().toString(), params);
            }

            @JsonValue
            public McpSchema.JSONRPCRequest toJSONRPCRequest() {
                return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            }
        }

        record PassthroughToolsetRequest(@JsonValue McpSchema.JSONRPCRequest toJSONRPCRequest) implements MpcToolsetRequest {}

    }

    public sealed interface MpcParam  {

        record MpcArsParam(@JsonValue Map<String, MpcArg> args) implements MpcParam {}

        record NameParam(@JsonValue String name) implements MpcParam {}

        sealed interface MpcArg {

            record Sql(@JsonValue String sql) implements MpcArg {}

            record OtherArgs(@JsonValue Map<String, Object> other) implements MpcArg {}

            record OtherArg(@JsonValue String other) implements MpcArg {}

        }
    }

}
