package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.request.WithRetryParams;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.support.RetryTemplateBuilder;

import java.util.Optional;

public interface RetryableClient<T extends WithRetryParams, U> {

    Logger log = LoggerFactory.getLogger(RetryableClient.class);

    Result<U, DataSourceClient.Err> doSend(T t);

    default Result<U, DataSourceClient.Err> callWithRetry(T req) {
        return Optional.ofNullable(req.getRetryParameters())
                .filter(r -> r.numRetries() > 1)
                .map(rp -> new RetryTemplateBuilder()
                        .maxAttempts(rp.numRetries())
                        .withListener(new RetryListener() {
                            @Override
                            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                                log.error(throwable.getMessage(), throwable);
                                RetryListener.super.onError(context, callback, throwable);
                            }
                        })
                        .build()
                        .execute(r -> doSend(req)))
                .orElseGet(() -> doSend(req));
    }

}
