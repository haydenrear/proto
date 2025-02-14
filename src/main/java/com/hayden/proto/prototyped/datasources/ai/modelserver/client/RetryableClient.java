package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.request.WithRetryParams;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;
import org.springframework.retry.support.RetryTemplateBuilder;

import java.util.Optional;

public interface RetryableClient<T extends WithRetryParams, U> {

    Result<U, DataSourceClient.Err> doSend(T t);

    default Result<U, DataSourceClient.Err> callWithRetry(T req) {
        return Optional.ofNullable(req.getRetryParameters())
                .filter(r -> r.numRetries() > 1)
                .map(rp -> new RetryTemplateBuilder()
                        .maxAttempts(rp.numRetries())
                        .build()
                        .execute(r -> doSend(req)))
                .orElseGet(() -> doSend(req));
    }

}
