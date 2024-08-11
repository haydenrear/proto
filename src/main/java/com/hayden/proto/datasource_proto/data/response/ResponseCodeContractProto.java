package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.datasource_proto.data.value.DiscreteContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface ResponseCodeContractProto extends ResponseConstructContractProto
    permits
        ResponseCodeContractProto.PermittingNumber {

    record PermittingNumber(DiscreteContractProto<Integer> permitting)
            implements Permitting<DiscreteContractProto<Integer>>, ResponseCodeContractProto {}

}
