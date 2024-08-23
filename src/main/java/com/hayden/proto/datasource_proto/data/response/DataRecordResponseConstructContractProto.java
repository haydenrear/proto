package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.Plural;

public non-sealed interface DataRecordResponseConstructContractProto extends ResponseConstructContractProto {
    Plural<? extends DataRecordContractProto> contract();
}
