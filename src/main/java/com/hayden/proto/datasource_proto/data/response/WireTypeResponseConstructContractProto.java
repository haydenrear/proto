package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;

public non-sealed interface WireTypeResponseConstructContractProto extends ResponseConstructContractProto {
    WireContractProto contract();
}
