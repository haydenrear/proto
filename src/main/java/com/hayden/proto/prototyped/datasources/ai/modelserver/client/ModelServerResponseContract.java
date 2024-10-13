package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.datasource.data.response.ResponseConstructContractProto;
import com.hayden.proto.prototype.datasource.data.response.WireTypeResponseConstructContractProto;

public record ModelServerResponseContract()
            implements DataSourceClient.DataRecordResponseContract {

    @Override
    public Any<ResponseConstructContractProto> responseContracts() {
        return new Any<ResponseConstructContractProto>() {
            @Override
            public ResponseConstructContractProto[] ofMany() {
                return new ResponseConstructContractProto[0];
            }
        };
    }

    @Override
    public WireTypeResponseConstructContractProto wire() {
        return null;
    }
}
