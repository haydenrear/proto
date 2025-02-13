package com.hayden.proto.prototyped.sources.client;

import com.hayden.proto.prototype.datasource.client.DataSourceClientContractProto;
import com.hayden.proto.prototype.datasource.data.response.ResponseContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestContractProto;
import com.hayden.proto.prototyped.CompositePrototypedBehavior;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.agg.AggregateError;
import com.hayden.utilitymodule.result.error.SingleError;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface DataSourceClient<REQ, RES> extends CompositePrototypedBehavior<DataSourceClientContractProto> {

    interface DataRecordResponseContract extends ResponseContractProto {}

    interface DataRecordRequestContract extends RequestContractProto {}

    record DataSourceClientPrototypeError(Set<SingleError> errors)  implements AggregateError.StdAggregateError {
        public DataSourceClientPrototypeError(String errors) {
            this(Arrays.stream(errors.split(",")).map(SingleError::fromMessage).collect(Collectors.toSet()));
        }

        public DataSourceClientPrototypeError(List<DataSourceClientPrototypeError> errors) {
            this(errors.stream().map(DataSourceClientPrototypeError::getMessage).collect(Collectors.joining(", ")));
        }
    }

    Result<RES, DataSourceClientPrototypeError> send(REQ request);

}