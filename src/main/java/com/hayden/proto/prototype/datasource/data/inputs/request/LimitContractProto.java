package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.value.NumberContractProto;
import com.hayden.proto.prototype.Permitting;

public sealed interface LimitContractProto extends RequestConstructContractProto
        permits
            LimitContractProto.PermitsNumberContractProto {

    record PermitsNumberContractProto(NumberContractProto permitting)
            implements Permitting<NumberContractProto>, LimitContractProto {}

}