package com.hayden.proto.prototyped.datasources.ai.modelserver.response;

import io.modelcontextprotocol.kotlin.sdk.PromptMessageContent;

import java.util.List;

public sealed interface ContextResponse
        permits
            ContextResponse.MpcPromptMessageContent {


    record MpcPromptMessageContent(List<PromptMessageContent> toAddToContext) implements ContextResponse {}

}
