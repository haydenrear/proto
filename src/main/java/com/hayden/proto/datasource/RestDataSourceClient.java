package com.hayden.proto.datasource;

import com.hayden.proto.datasource.inputs.request.rest.RestRequest;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.proto.datasource_proto.data.wiretype.StaticWireProto;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.utilitymodule.reflection.TypeReferenceDelegate;
import com.hayden.utilitymodule.result.Result;

public class RestDataSourceClient <T, RES_WIRE extends WireContractProto>
        implements DataSourceClient<T, RestRequest<T>, DataSourceClient.DataRecordResponseRecord<RES_WIRE>, StaticWireProto, RES_WIRE>
{
    @Override
    public  Result<DataRecordResponseRecord<RES_WIRE>, DataSourceClientPrototypeError> send(RestRequest<T> request) {
        return null;
    }

    @Override
    public DataSourceClientContractProto proto() {
        return null;
    }
}
