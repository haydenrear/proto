package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import org.springframework.ai.mcp.client.transport.ServerParameters;
import org.springframework.ai.mcp.spec.McpSchema;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
public interface ModelContextProtocolContextRequest {

    Optional<MpcServerDescriptor> serverDescriptor();

    Optional<MpcToolsetRequest> toCallToolRequest();

    @Builder
    record MpcServerDescriptor(ServerParameters serverParameters) {}


    sealed interface MpcToolsetRequest {

        McpSchema.JSONRPCRequest toJSONRPCRequest();

        @Builder
        record SerMpcToolsetRequest(String jsonRpc, String method, String id, Map<String, MpcParam> params) implements MpcToolsetRequest {

            public SerMpcToolsetRequest(Map<String, MpcParam> params) {
                this("2.0", "tools/call", UUID.randomUUID().toString(), params);
            }

            public McpSchema.JSONRPCRequest toJSONRPCRequest() {
                return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            }
        }

        record PassthroughToolsetRequest(McpSchema.JSONRPCRequest toJSONRPCRequest) implements MpcToolsetRequest {}

    }

    sealed interface MpcParam  {

        record MpcArsParam(@JsonValue Map<String, MpcArg> args) implements MpcParam {}

        record NameParam(@JsonValue String name) implements MpcParam {}

        sealed interface MpcArg {

            record Sql(@JsonValue String sql) implements MpcArg {}

            record OtherArgs(@JsonValue Map<String, Object> other) implements MpcArg {}

            record OtherArg(@JsonValue String other) implements MpcArg {}

        }
    }

}
