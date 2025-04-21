package com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.Builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record ModelContextProtocolContextRequest(MpcToolsetRequest.PassthroughToolsetRequest toolsetRequest,
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

    public record ToolsetRequest(McpSchema.CallToolRequest params, String id, String method) {
        @JsonProperty("jsonrpc")
        public String jsonRpc() {
            return "2.0";
        }
    }

    public sealed interface MpcToolsetRequest permits CommitDiffToolSchema, MpcToolsetRequest.PassthroughToolsetRequest, PostgresToolSchema {

        record PassthroughToolsetRequest(McpSchema.CallToolRequest req, String id, String method) implements MpcToolsetRequest {
            @Override
            public ToolsetRequest callToolRequest() {
                return new ToolsetRequest(req, id, method);
            }
        }

        ToolsetRequest callToolRequest();

    }


}
