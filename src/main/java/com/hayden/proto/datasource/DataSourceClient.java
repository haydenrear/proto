package com.hayden.proto.datasource;

import com.hayden.proto.datasource.inputs.request.Request;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.proto.datasource_proto.data.response.ResponseContractProto;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.ty.CompositePrototypedBehavior;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.AggregateError;
import com.hayden.utilitymodule.result.error.ErrorCollect;

import java.util.Set;

public interface DataSourceClient<
            T,
            REQ extends Request<T, REQ_WIRE>,
            RES extends DataSourceClient.DataRecordResponseRecord,
            REQ_WIRE extends WireContractProto,
            RES_WIRE extends WireContractProto
        >
        extends CompositePrototypedBehavior<DataSourceClientContractProto> {

    interface DataRecordResponseContract extends ResponseContractProto {}
    interface DataRecordResponseRecord extends ResponseRecord<DataRecordResponseContract> {}

    record DataSourceClientPrototypeError(Set<ErrorCollect> errors)  implements AggregateError { }

    Result<RES, DataSourceClientPrototypeError> send(REQ request);

}