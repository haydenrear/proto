package com.hayden.proto.datasource;

import com.hayden.proto.datasource.inputs.request.Request;
import com.hayden.proto.datasource_proto.DataRecordContractProto;
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
            RES extends DataSourceClient.DataRecordResponseRecord<RES_WIRE>,
            REQ_WIRE extends WireContractProto,
            RES_WIRE extends WireContractProto
        >
        extends CompositePrototypedBehavior<DataSourceClientContractProto> {

    interface DataRecordResponseContract<W extends WireContractProto>
            extends ResponseContractProto<W, DataRecordContractProto> {}
    interface DataRecordResponseRecord<W extends WireContractProto>
            extends ResponseRecord<W, DataRecordContractProto, DataRecordResponseContract<W>> {}

    record DataSourceClientPrototypeError(Set<ErrorCollect> errors)  implements AggregateError { }

    Result<RES, DataSourceClientPrototypeError> send(REQ request);

}