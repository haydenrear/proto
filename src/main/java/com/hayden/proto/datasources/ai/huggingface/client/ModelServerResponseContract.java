package com.hayden.proto.datasources.ai.huggingface.client;

import com.hayden.proto.datasource.DataSourceClient;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.data.response.ResponseConstructContractProto;

public interface ModelServerResponseContract extends DataSourceClient.DataRecordResponseContract {
    @Override
    Any<ResponseConstructContractProto> responseContracts();
}
