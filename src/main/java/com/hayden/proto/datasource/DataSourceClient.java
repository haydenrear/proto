package com.hayden.proto.datasource;

import com.google.common.collect.Sets;
import com.hayden.proto.datasource.inputs.request.Request;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.proto.datasource_proto.data.response.ResponseContractProto;
import com.hayden.proto.datasource_proto.inputs.request.RequestContractProto;
import com.hayden.proto.proto.Prototype;
import com.hayden.proto.ty.CompositePrototypedBehavior;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.AggregateError;
import com.hayden.utilitymodule.result.error.ErrorCollect;
import com.hayden.utilitymodule.stream.StreamUtil;

import java.util.Set;

public interface DataSourceClient<REQ extends Request, RES extends DataSourceClient.DataRecordResponseRecord>
        extends CompositePrototypedBehavior<DataSourceClientContractProto> {

    interface DataRecordResponseContract extends ResponseContractProto {}
    interface DataRecordRequestContract extends RequestContractProto {}

    interface DataRecordResponseRecord extends ResponseRecord {}

    record DataSourceClientPrototypeError(Set<ErrorCollect> errors)  implements AggregateError {
        public DataSourceClientPrototypeError(String message) {
            this(Sets.newHashSet(ErrorCollect.fromMessage(message)));
        }
    }

    default Plural<? extends Prototype> contracts() {
        return (Any<Prototype>) () -> StreamUtil.StreamBuilderDelegate.builder()
                .addAllStreams(
                        proto().requestContracts().pluralize(),
                        proto().responseContracts().pluralize()
                )
                .build()
                .toArray(Prototype[]::new);
    }

    Result<RES, DataSourceClientPrototypeError> send(REQ request);

}