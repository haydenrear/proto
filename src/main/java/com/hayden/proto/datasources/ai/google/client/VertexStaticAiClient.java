package com.hayden.proto.datasources.ai.google.client;

import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.response.ResponseConstructContractProto;
import com.hayden.proto.datasource_proto.data.response.WireTypeResponseConstructContractProto;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.datasources.ai.google.data.VertexStaticWireProto;
import com.hayden.proto.datasources.ai.google.request.VertexRequest;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.utilitymodule.result.Result;

public class VertexStaticAiClient implements VertexClient<VertexStaticAiClient.VertexStaticResponseRecord> {

    public record VertexStaticResponseRecord(GenerateContentResponse contentResponses)
            implements DataRecordResponseRecord {
        @Override
        public DataRecordResponseContract proto() {
            return new DataRecordResponseContract() {
                @Override
                public Plural<ResponseConstructContractProto> responseContracts() {
                    return null;
                }

                @Override
                public WireTypeResponseConstructContractProto wire() {
                    return VertexStaticWireProto::new;
                }
            };
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
