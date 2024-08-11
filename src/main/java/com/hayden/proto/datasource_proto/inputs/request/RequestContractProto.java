package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.ManyOf;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.proto.CompositePrototype;

public interface RequestContractProto<W extends WireContractProto>
        extends CompositePrototype {

    ManyOf<? extends RequestConstructContractProto> requestItems();

    W wireContract();

}
