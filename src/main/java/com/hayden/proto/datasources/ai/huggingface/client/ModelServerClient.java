package com.hayden.proto.datasources.ai.huggingface.client;

import com.hayden.proto.datasource.DataSourceClient;
import com.hayden.proto.datasource.WrappingApiDataSourceClient;
import com.hayden.proto.datasource.inputs.request.api.StringSessionKey;
import com.hayden.proto.datasources.ai.google.request.VertexRequest;
import com.hayden.proto.datasources.ai.huggingface.request.ModelServerRequest;

public interface ModelServerClient<RES extends DataSourceClient.DataRecordResponseRecord>
        extends WrappingApiDataSourceClient<RES, ModelServerRequest> {


}
