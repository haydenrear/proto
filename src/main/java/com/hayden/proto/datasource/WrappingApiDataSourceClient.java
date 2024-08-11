package com.hayden.proto.datasource;

import com.hayden.proto.datasource.inputs.request.api.ApiRequest;
import com.hayden.proto.datasource_proto.data.wiretype.StaticWireProto;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;

public interface WrappingApiDataSourceClient<
            T,
            WIRE_CT extends WireContractProto,
            RES extends DataSourceClient.DataRecordResponseRecord<WIRE_CT>,
            REQ extends ApiRequest<T, StaticWireProto>
        >

        extends DataSourceClient<T, REQ, RES, StaticWireProto, WIRE_CT> {

}
