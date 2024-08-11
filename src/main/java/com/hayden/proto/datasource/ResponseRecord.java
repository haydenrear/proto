package com.hayden.proto.datasource;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.data.response.ResponseContractProto;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.ty.CompositePrototyped;

public interface ResponseRecord<W extends WireContractProto, D extends DataRecordContractProto, R extends ResponseContractProto<W, D>>
        extends CompositePrototyped<R> {



}
