package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.utilitymodule.MapFunctions;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.SingleError;
import io.modelcontextprotocol.client.McpAsyncClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.mcp.AsyncMcpToolCallback;
import org.springframework.ai.mcp.SyncMcpToolCallback;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.util.ToolUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicMcpToolCallbackProvider implements ToolCallbackProvider {

    public record ToolMetadata(String toolName) {}

    public record McpError(String getMessage) implements SingleError {}

    private final ConcurrentHashMap<ToolMetadata, McpClientDelegate> mcpClients;

    public sealed interface McpClientDelegate {

        record SyncClientDelegate(@Delegate McpSyncClient syncClient) implements McpClientDelegate { }

        record AsyncClientDelegate(@Delegate McpAsyncClient syncClient) implements McpClientDelegate {

            public McpSchema.ListToolsResult listTools() {
                return this.syncClient.listTools().block();
            }

            public McpSchema.CallToolResult callTool(McpSchema.CallToolRequest toolName) {
                return this.syncClient.callTool(toolName).block();
            }

        }

        McpSchema.CallToolResult callTool(McpSchema.CallToolRequest request);

        McpSchema.ListToolsResult listTools();

        McpSchema.Implementation getClientInfo();

        McpSchema.ClientCapabilities getClientCapabilities();

        McpSchema.Implementation getServerInfo();

    }

    public DynamicMcpToolCallbackProvider(List<McpSyncClient> mcpClients, List<McpAsyncClient> asyncClients) {
        var sync = mcpClients.stream().map(McpClientDelegate.SyncClientDelegate::new).toList();
        var async = asyncClients.stream().map(McpClientDelegate.AsyncClientDelegate::new).toList();
        List<McpClientDelegate> all = new ArrayList<>();
        all.addAll(sync);
        all.addAll(async);
        this.mcpClients = parseClients(all);
    }

    private static @NotNull ConcurrentHashMap<ToolMetadata, McpClientDelegate> parseClients(List<McpClientDelegate> mcpClients) {
        return MapFunctions.CollectMapDoOnDuplicates(
                mcpClients.stream()
                        .flatMap(c -> c.listTools().tools().stream()
                                .map(t -> Map.entry(new ToolMetadata(t.name()), c))),
                (f, s) -> {throw new RuntimeException("Cannot have same tool definition!");},
                ConcurrentHashMap::new);
    }

    public Result<McpClientDelegate, McpError> retrieve(ServerParameters params, McpSchema.CallToolRequest callToolRequest) {
        return Result.fromOptOrErr(
                mcpClients.entrySet().stream()
                        .filter(entry -> entry.getValue().listTools()
                                .tools().stream()
                                .anyMatch(d -> Objects.equals(d.name(), callToolRequest.name())))
                        .findAny()
                        .map(Map.Entry::getValue),
                new McpError("Could not find model context protocol matching %s. Please call register first.".formatted(params)));
    }

    public void addMcpClient(McpSchema.Tool t, McpClientDelegate client) {
        ToolMetadata key = new ToolMetadata(t.name());
        if (mcpClients.containsKey(key)) {
            throw new RuntimeException("Duplicate tool found: " + key);
        }
        this.mcpClients.putIfAbsent(key, client);
    }

    public void register(McpSyncClient clientToAdd) {
        clientToAdd.listTools().tools()
                .forEach(t -> addMcpClient(t, new McpClientDelegate.SyncClientDelegate(clientToAdd)));
    }

    public void register(McpAsyncClient clientToAdd) {
        clientToAdd.listTools().map(McpSchema.ListToolsResult::tools)
                .flux()
                .flatMap(Flux::fromIterable)
                .subscribe(t -> addMcpClient(t, new McpClientDelegate.AsyncClientDelegate(clientToAdd)));
    }

    @Override
    public ToolCallback[] getToolCallbacks() {
        var toolCallbacks = new ArrayList<ToolCallback>();
        mcpClients.values().forEach(mcpClient -> {
            if (mcpClient instanceof McpClientDelegate.AsyncClientDelegate(McpAsyncClient syncClient)) {
                toolCallbacks.addAll(mcpClient.listTools()
                        .tools()
                        .stream()
                        .map(tool -> new AsyncMcpToolCallback(syncClient, tool))
                        .toList());
            }
            if (mcpClient instanceof McpClientDelegate.SyncClientDelegate(McpSyncClient syncClient)) {
                toolCallbacks.addAll(mcpClient.listTools()
                        .tools()
                        .stream()
                        .map(tool -> new SyncMcpToolCallback(syncClient, tool))
                        .toList());
            }
        });
        var array = toolCallbacks.toArray(ToolCallback[]::new);
        validateToolCallbacks(array);
        return array;
    }

    private void validateToolCallbacks(ToolCallback[] toolCallbacks) {
        List<String> duplicateToolNames = ToolUtils.getDuplicateToolNames(toolCallbacks);
        if (!duplicateToolNames.isEmpty()) {
            throw new IllegalStateException(
                    "Multiple tools with the same name (%s)".formatted(String.join(", ", duplicateToolNames)));
        }
    }
}
