package com.hayden.proto.datasources.ai.google.client;

import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.hayden.proto.datasources.ai.google.data.VertexStaticWireProto;
import com.hayden.proto.datasources.ai.google.request.VertexRequest;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.utilitymodule.result.Result;

public class VertexStaticAiClient implements VertexClient<VertexStaticWireProto, VertexStaticAiClient.VertexStaticResponseRecord> {

    public record VertexStaticResponseRecord(GenerateContentResponse contentResponses)
            implements DataRecordResponseRecord<VertexStaticWireProto> {
        @Override
        public DataRecordResponseContract<VertexStaticWireProto> proto() {
            return null;
        }
    }

    @Override
    public Result<VertexStaticResponseRecord, DataSourceClientPrototypeError> send(VertexRequest request) {
        return null;
    }

    @Override
    public DataSourceClientContractProto proto() {
        return null;
    }
}
