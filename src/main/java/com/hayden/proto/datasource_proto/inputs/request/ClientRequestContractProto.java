package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;

public interface ClientRequestContractProto<W extends WireContractProto> extends RequestContractProto {

    Plural<? extends RequestConstructContractProto> requestContracts();

}
