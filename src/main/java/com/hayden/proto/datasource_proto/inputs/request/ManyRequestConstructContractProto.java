package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.Many;

public interface ManyRequestConstructContractProto<T extends RequestConstructContractProto>
        extends
            RequestConstructContractProto,
            Many<T> {



}
