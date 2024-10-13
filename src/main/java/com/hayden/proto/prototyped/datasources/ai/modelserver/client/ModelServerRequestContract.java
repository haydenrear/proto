package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.datasource.data.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.data.ModelServerRecordProto;

public record ModelServerRequestContract(int contextLength, String modelEndpoint, ModelServerRecordProto permitting)
        implements DataSourceClient.DataRecordRequestContract {

    @Override
    public Any<AiRequestConstructProto> requestContracts() {
        return () -> new AiRequestConstructProto[]{
                new AiRequestConstructProto.AiContextLengthProtoValue.PermitsNumberContractProto(contextLength),
                new AiRequestConstructProto.AiModelLocationProtoValue.PermitsStringContractProto(modelEndpoint),
                new AiRequestConstructProto.AiRestContract.AiRestBody(permitting)
        };
    }


}
