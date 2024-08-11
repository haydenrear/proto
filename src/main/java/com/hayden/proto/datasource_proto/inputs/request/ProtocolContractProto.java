package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.data.value.DiscreteContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface ProtocolContractProto extends RequestConstructContractProto
        permits
            ProtocolContractProto.PermitsDiscretePatternProto {

    record PermitsDiscretePatternProto(DiscreteContractProto<String> permitting)
            implements Permitting<DiscreteContractProto<String>>, ProtocolContractProto {}

}
