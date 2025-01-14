package com.hayden.proto.prototyped.datasources.ai.modelserver.client

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.PromptMessageContent
import io.modelcontextprotocol.kotlin.sdk.client.Client
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class ModelContextProtocolClientAdapter {

    val clientImplementations = mutableMapOf<Implementation, Client>()

    fun doCallClient(impl: Implementation, callToolRequest: CallToolRequest): List<PromptMessageContent>? {
        return runBlocking {
            clientImplementations.getOrPut(impl) { Client(impl) }
                .callTool(callToolRequest)
                ?.content
        }
    }

}