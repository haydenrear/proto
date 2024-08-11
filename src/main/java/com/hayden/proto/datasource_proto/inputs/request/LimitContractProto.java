package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.data.value.NumberContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface LimitContractProto extends RequestConstructContractProto
        permits
            LimitContractProto.PermitsNumberContractProto {

    record PermitsNumberContractProto(NumberContractProto permitting)
            implements Permitting<NumberContractProto>, LimitContractProto {}

}