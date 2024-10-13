package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.wire.WireContractProto;

public interface ClientRequestContractProto<W extends WireContractProto> extends RequestContractProto {

    Plural<? extends RequestConstructContractProto> requestContracts();

}
