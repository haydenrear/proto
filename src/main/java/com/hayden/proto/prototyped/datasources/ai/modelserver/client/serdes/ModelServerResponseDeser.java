package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;

public interface ModelServerResponseDeser<T extends ModelServerResponse> {

    Result<T, DataSourceClient.Err> deserialize(String toParser) ;

    boolean matches(String matcher);

}
