package com.hayden.proto.datasources.ai.google.client;

import com.hayden.proto.datasources.ai.google.request.VertexRequest;
import com.hayden.proto.datasource.DataSourceClient;
import com.hayden.proto.datasource.WrappingApiDataSourceClient;
import com.hayden.proto.datasource.inputs.request.api.StringSessionKey;

public interface VertexClient<RES extends DataSourceClient.DataRecordResponseRecord>
        extends WrappingApiDataSourceClient<RES, VertexRequest> {

    record VertexSessionKey(String wrapped) implements StringSessionKey { }


}
