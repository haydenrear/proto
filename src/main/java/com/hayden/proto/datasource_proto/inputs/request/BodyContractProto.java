package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.inputs.search.SearchContractProto;
import com.hayden.proto.permitting.Permitting;
import com.hayden.proto.proto.DelegatingPrototype;

public sealed interface BodyContractProto
        extends
                RequestConstructContractProto,
                DelegatingPrototype<BodyContractProto>
        permits
                BodyContractProto.PermitsSearchContractProto {

    record PermitsSearchContractProto(SearchContractProto permitting)
            implements Permitting<SearchContractProto>, BodyContractProto, DelegatingPrototype<BodyContractProto> {
    }

    default BodyContractProto delegator() {
        return this;
    }
}