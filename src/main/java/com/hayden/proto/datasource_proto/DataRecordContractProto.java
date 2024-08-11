package com.hayden.proto.datasource_proto;

import com.hayden.proto.datasource_proto.cardinality.Many;
import com.hayden.proto.datasource_proto.data.value.DataValueContractProto;
import com.hayden.proto.proto.Prototype;

public interface DataRecordContractProto extends Prototype {

    Many<DataValueContractProto> values();

}
