package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.inputs.search.SearchContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface BodyContractProto extends RequestConstructContractProto
        permits
            BodyContractProto.PermitsSearchContractProto {

    record PermitsSearchContractProto(SearchContractProto permitting)
            implements Permitting<SearchContractProto>, BodyContractProto {}

}