package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.value.DiscreteContractProto;
import com.hayden.proto.prototype.Permitting;

public sealed interface ProtocolContractProto extends RequestConstructContractProto
        permits
            ProtocolContractProto.PermitsDiscretePatternProto {

    record PermitsDiscretePatternProto(DiscreteContractProto<String> permitting)
            implements Permitting<DiscreteContractProto<String>>, ProtocolContractProto {}

}
