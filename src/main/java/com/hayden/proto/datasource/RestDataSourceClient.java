package com.hayden.proto.datasource;

import com.hayden.proto.datasource.inputs.request.rest.RestRequest;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.utilitymodule.result.Result;

public class RestDataSourceClient <T>
        implements DataSourceClient<RestRequest<T>, DataSourceClient.DataRecordResponseRecord>
{
    @Override
    public  Result<DataRecordResponseRecord, DataSourceClientPrototypeError> send(RestRequest<T> request) {
        return null;
    }

    @Override
    public DataSourceClientContractProto proto() {
        return null;
    }
}
