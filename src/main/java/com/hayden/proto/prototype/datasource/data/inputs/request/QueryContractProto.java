package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.inputs.search.SearchContractProto;
import com.hayden.proto.prototype.Permitting;

public sealed interface QueryContractProto extends RequestConstructContractProto
        permits
            QueryContractProto.PermitsSearchContractProto {

    record PermitsSearchContractProto(SearchContractProto permitting)
            implements Permitting<SearchContractProto>, QueryContractProto {}

}