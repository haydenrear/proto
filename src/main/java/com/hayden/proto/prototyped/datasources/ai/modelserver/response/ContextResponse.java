package com.hayden.proto.prototyped.datasources.ai.modelserver.response;


import io.modelcontextprotocol.spec.McpSchema;

import java.util.List;

public sealed interface ContextResponse
        permits
            ContextResponse.MpcPromptMessageContent {


    record MpcPromptMessageContent(List<McpSchema.Content> toAddToContext) implements ContextResponse {}

}
