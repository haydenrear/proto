package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.ManyOf;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.utilitymodule.stream.StreamUtil;

public interface ClientRequestContractProto<W extends WireContractProto> extends RequestContractProto<W> {

    Plural<? extends RequestConstructContractProto> requestItems();

}
