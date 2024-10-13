package com.hayden.proto.prototype.datasource.data.response;

import com.hayden.proto.prototype.datasource.DataRecordContractProto;
import com.hayden.proto.prototype.cardinality.Plural;

public non-sealed interface DataRecordResponseConstructContractProto
        extends ResponseConstructContractProto {

    Plural<? extends DataRecordContractProto> contract();

}
