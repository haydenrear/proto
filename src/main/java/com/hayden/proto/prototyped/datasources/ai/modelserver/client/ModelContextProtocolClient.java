package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelContextProtocolContextRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelContextProtocolRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ContextResponse;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.SingleError;
import io.modelcontextprotocol.kotlin.sdk.PromptMessageContent;
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
    ModelContextProtocolClientAdapter adapter;
    @Autowired
    ObjectMapper objectMapper;


    public Result<ModelServerResponse, DataSourceClient.DataSourceClientPrototypeError> send(ModelContextProtocolRequest request) {
        return callWithRetry(request);
    }

    @RequestResponse(requestSource = ModelContextProtocolRequest.class, responseSource = ModelServerResponse.class)
    public Result<ModelServerResponse, DataSourceClient.DataSourceClientPrototypeError> doSend(ModelContextProtocolRequest request) {
        log.info("send request: {}", request);

        // aggregate all the errors for each of the many requests, not failing if only one of them fails but propagating info about each one
        var res = Result.<ModelContextProtocolContextRequest, DataSourceClient.DataSourceClientPrototypeError>
                        stream(request.getContent().prompt().stream().parallel())
                .filterResult(Objects::nonNull)
                .flatMapResult(cr -> Result.fromOptOrErr(
                                cr.serverDescriptor(),
                                () -> new DataSourceClient.DataSourceClientPrototypeError("Server descriptor was not found for %s.".formatted(cr)))
                        // This toCallToolRequest will be calling LLM and translating
                        .flatMapResult(i -> Result.fromOptOrErr(
                                        cr.toCallToolRequest(),
                                        () -> new DataSourceClient.DataSourceClientPrototypeError("Tool call request was not found for %s.".formatted(cr)))
                                .flatMapResult(cte -> doCallClientToPromptMessageContent(i, cte))
                                .flatMapResult(cte -> Result
                                        .<ContextResponse, DataSourceClient.DataSourceClientPrototypeError>ok(new ContextResponse.MpcPromptMessageContent(cte)))));

        var collected = res.toList();

        var result = new ModelServerResponse.RetrievedContextResponse(collected.results());

       return collected.errsList().isEmpty()
               ? Result.ok(result)
               : Result.from(result, new DataSourceClient.DataSourceClientPrototypeError(collected.errsList()));
    }

    private @Nullable Result<List<PromptMessageContent>, DataSourceClient.DataSourceClientPrototypeError> doCallClientToPromptMessageContent(ModelContextProtocolContextRequest.MpcServerDescriptor i,
                                                                                                                                             ModelContextProtocolContextRequest.MpcToolsetRequest cte) {
        return Result.fromOptOrErr(
                        // TODO: Result.fromMono
                        adapter.doCallClient(i.serverParameters(), cte.toJSONRPCRequest()).blockOptional(),
                        () -> new DataSourceClient.DataSourceClientPrototypeError("Received empty response from mpc server."))
                .flatMap(js -> {
                    try {
                        if (js.result() instanceof Map m) {
                            if (m.containsKey("isError") && m.get("isError") instanceof Boolean b && b) {
                                return Result.err(new DataSourceClient.DataSourceClientPrototypeError("Received error from mpc server: %s."
                                        .formatted(js.error())));
                            }

                            if (!m.containsKey("content")) {
                                return Result.err(new DataSourceClient.DataSourceClientPrototypeError("Result from mpc server did not contain any content."));
                            }

                            var found = objectMapper.writeValueAsString(m.get("content"));
                            return Result.ok(objectMapper.readValue(found, new TypeReference<List<PromptMessageContent>>() {}));
                        }

                        return Result.err(new DataSourceClient.DataSourceClientPrototypeError("JSON response from mpc server was of unknown type: %s."
                                .formatted(js.result())));
                    } catch (IOException |
                             NullPointerException e) {
                        return Result.err(new DataSourceClient.DataSourceClientPrototypeError(SingleError.parseStackTraceToString(e)));
                    }
                });
    }

}