package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelContextProtocolRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ContextResponse;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototyped.sources.client.RequestResponse;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.SingleError;
import io.modelcontextprotocol.kotlin.sdk.PromptMessageContent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
@DataClient(proto = ContextModelServerContractProto.class)
public class ModelContextProtocolClient {

    @Autowired
    ModelContextProtocolClientAdapter adapter;

    @RequestResponse(requestSource = ModelContextProtocolRequest.class, responseSource = ModelServerResponse.class)
    public Result<ModelServerResponse, DataSourceClient.DataSourceClientPrototypeError> send(ModelContextProtocolRequest request) {
        Result<ContextResponse, DataSourceClient.DataSourceClientPrototypeError> res = Result.<ContextResponse, DataSourceClient.DataSourceClientPrototypeError>from(
                request.getContent().prompt()
                        .parallelStream()
                        .filter(Objects::nonNull)
                        .map(cr -> cr.toImplementation()
                                .flatMap(i -> cr.toCallToolRequest()
                                        .map(cte -> Result.ok(new ContextResponse.MpcPromptMessageContent(adapter.doCallClient(i, cte)))))
                                .orElseGet(() -> Result.err(new DataSourceClient.DataSourceClientPrototypeError("Could not convert %s to MPC request.".formatted(cr))))));

        var collected = res.toList();

        Set<SingleError> aggErr = collected.errsList().stream()
                .flatMap(dpe -> dpe.errors().stream())
                .collect(Collectors.toSet());

        var result = new ModelServerResponse.RetrievedContextResponse(collected.results());
        return aggErr.isEmpty()
               ? Result.ok(result)
               : Result.from(result, new DataSourceClient.DataSourceClientPrototypeError(aggErr));
    }

}
