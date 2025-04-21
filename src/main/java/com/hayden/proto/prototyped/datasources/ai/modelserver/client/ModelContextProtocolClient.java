package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp.ModelContextProtocolContextRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp.ModelContextProtocolRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ContextResponse;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.SingleError;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
@DataClient(proto = ContextModelServerContractProto.class)
public class ModelContextProtocolClient implements RetryableClient<ModelContextProtocolRequest, ModelServerResponse> {


    @Autowired
    DelegatingSyncMcpClient syncMcpClient;

    public Result<ModelServerResponse, DataSourceClient.Err> send(ModelContextProtocolRequest request) {
        return callWithRetry(request);
    }

    @RequestResponse(requestSource = ModelContextProtocolRequest.class, responseSource = ModelServerResponse.class)
    public Result<ModelServerResponse, DataSourceClient.Err> doSend(ModelContextProtocolRequest request) {
        log.info("send request: {}", request);

        // aggregate all the errors for each of the many requests, not failing if only one of them fails but propagating info about each one
        var res = Result.<ModelContextProtocolContextRequest, DataSourceClient.Err>
                        stream(request.getContent().prompt().stream().parallel())
                .filterResult(Objects::nonNull)
                .flatMapResult(cr -> Result.fromOptOrErr(
                                cr.serverDescriptor(),
                                () -> new DataSourceClient.Err("Server descriptor was not found for %s.".formatted(cr)))
                        // This toCallToolRequest will be calling LLM and translating
                        .flatMapResult(i -> Result.fromOptOrErr(
                                        cr.toCallToolRequest(),
                                        () -> new DataSourceClient.Err("Tool call request was not found for %s.".formatted(cr)))
                                .flatMapResult(cte -> doCallClientToContent(i, cte))
                                .flatMapResult(cte -> Result
                                        .<ContextResponse, DataSourceClient.Err>ok(new ContextResponse.MpcPromptMessageContent(cte)))));

        var collected = res.toList();

        var result = new ModelServerResponse.RetrievedContextResponse(collected.results());

       return collected.errsList().isEmpty()
               ? Result.ok(result)
               : Result.from(result, new DataSourceClient.Err(collected.errsList()));
    }

    private @Nullable Result<List<McpSchema.Content>, DataSourceClient.Err> doCallClientToContent(ModelContextProtocolContextRequest.MpcServerDescriptor i,
                                                                                                  ModelContextProtocolContextRequest.MpcToolsetRequest cte) {
        return syncMcpClient.callTool(i.serverParameters(), cte.callToolRequest().params())
                .mapError(mcpError -> new DataSourceClient.Err(mcpError.getMessage()));
    }

}