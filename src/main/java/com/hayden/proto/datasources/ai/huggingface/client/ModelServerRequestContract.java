package com.hayden.proto.datasources.ai.huggingface.client;

import com.hayden.proto.datasource.DataSourceClient;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.inputs.request.ai_request.AiRequestConstructProto;

public interface ModelServerRequestContract extends DataSourceClient.DataRecordRequestContract {
    @Override
    Any<AiRequestConstructProto> requestContracts();
}
