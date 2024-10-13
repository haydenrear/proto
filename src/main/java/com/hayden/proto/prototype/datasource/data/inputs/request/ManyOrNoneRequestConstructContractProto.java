package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.cardinality.ManyOrNone;

public interface ManyOrNoneRequestConstructContractProto<T extends RequestConstructContractProto>
        extends
            RequestConstructContractProto,
            ManyOrNone<T> {



}
