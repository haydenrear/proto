package com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.modelcontextprotocol.spec.McpSchema;

public record ToolsetRequest(McpSchema.CallToolRequest params, String id, String method) {
    @JsonProperty("jsonrpc")
    public String jsonRpc() {
        return "2.0";
    }
}
