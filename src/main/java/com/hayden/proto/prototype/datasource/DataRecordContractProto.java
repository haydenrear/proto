package com.hayden.proto.prototype.datasource;

import com.hayden.proto.prototype.cardinality.Many;
import com.hayden.proto.prototype.value.DataValueContractProto;
import com.hayden.proto.prototype.Prototype;

public interface DataRecordContractProto extends Prototype {

    Many<? extends DataValueContractProto> values();

}
