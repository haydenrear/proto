package com.hayden.proto.datasources.ai.google.client;

import com.hayden.proto.datasources.ai.google.request.VertexRequest;
import com.hayden.proto.datasource.DataSourceClient;
import com.hayden.proto.datasource.WrappingApiDataSourceClient;
import com.hayden.proto.datasource.inputs.request.api.StringSessionKey;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;

public interface VertexClient<WIRE_CT extends WireContractProto, RES extends DataSourceClient.DataRecordResponseRecord>
        extends WrappingApiDataSourceClient<VertexRequest.VertexContent, WIRE_CT, RES, VertexRequest> {

    record VertexSessionKey(String wrapped) implements StringSessionKey { }


}
