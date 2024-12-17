package com.hayden.proto.prototyped.sources.client;

import com.hayden.proto.prototype.datasource.client.DataSourceClientContractProto;
import com.hayden.proto.prototype.datasource.data.response.ResponseContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestContractProto;
import com.hayden.proto.prototyped.CompositePrototypedBehavior;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.agg.AggregateError;
import com.hayden.utilitymodule.result.error.SingleError;

import java.util.Set;

public interface DataSourceClient<REQ, RES> extends CompositePrototypedBehavior<DataSourceClientContractProto> {

    interface DataRecordResponseContract extends ResponseContractProto {}

    interface DataRecordRequestContract extends RequestContractProto {}

    record DataSourceClientPrototypeError(Set<SingleError> errors)  implements AggregateError.StdAggregateError { }

    Result<RES, DataSourceClientPrototypeError> send(REQ request);

}