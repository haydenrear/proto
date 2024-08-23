package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.ManyOf;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.proto.CompositePrototype;

public interface RequestContractProto<W extends WireContractProto>
        extends CompositePrototype {

    Plural<? extends RequestConstructContractProto> requestItems();

    W wireContract();

}
