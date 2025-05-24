package com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp;

import io.modelcontextprotocol.spec.McpSchema;

public sealed interface MpcToolsetRequest permits CommitDiffToolSchema, MpcToolsetRequest.PassthroughToolsetRequest, PostgresToolSchema {

    record PassthroughToolsetRequest(McpSchema.CallToolRequest req, String id, String method) implements MpcToolsetRequest {
        @Override
        public ToolsetRequest callToolRequest() {
            return new ToolsetRequest(req, id, method);
        }
    }

    ToolsetRequest callToolRequest();

}
