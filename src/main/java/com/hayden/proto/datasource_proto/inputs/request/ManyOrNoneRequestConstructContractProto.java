package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.ManyOrNone;

public interface ManyOrNoneRequestConstructContractProto<T extends RequestConstructContractProto>
        extends
            RequestConstructContractProto,
            ManyOrNone<T> {



}
