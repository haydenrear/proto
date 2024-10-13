package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.cardinality.Many;

public interface ManyRequestConstructContractProto<T extends RequestConstructContractProto>
        extends
            RequestConstructContractProto,
            Many<T> {



}
