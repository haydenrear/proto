package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.proto.CompositePrototype;

public interface RequestContractProto
        extends CompositePrototype<RequestConstructContractProto> {

    default Plural<? extends RequestConstructContractProto> prototypes() {
        return requestContracts();
    }

    Plural<? extends RequestConstructContractProto> requestContracts();

    WireTypeRequestContractProto wire();


}
