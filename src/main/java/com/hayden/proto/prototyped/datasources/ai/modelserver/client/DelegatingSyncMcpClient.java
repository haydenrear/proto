package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.utilitymodule.result.Result;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DelegatingSyncMcpClient {

    @Autowired
    private DynamicMcpToolCallbackProvider callbackProvider;

    public Result<List<McpSchema.Content>, DynamicMcpToolCallbackProvider.McpError> callTool(ServerParameters params,
                                                                                             McpSchema.CallToolRequest request) {
        return this.callbackProvider.retrieve(params, request)
                .flatMapResult(syncClient -> {
                    var calledTool = syncClient.callTool(request);
                    if (calledTool.isError()) {
                        return Result.err(new DynamicMcpToolCallbackProvider.McpError(
                                "Error calling MCP %s: %s".formatted(request, calledTool.content().stream()
                                        .map(Object::toString).collect(Collectors.joining(", ")))));
                    }

                    return Result.ok(calledTool.content());
                });
    }

}
