package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

public interface WithRetryParams {

    RetryParameters getRetryParameters();

    void addExceptionMessage(String exceptionMessage);

}
