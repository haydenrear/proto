package com.hayden.proto.prototype.datasource.data.response;

import com.hayden.proto.prototype.value.DiscreteContractProto;
import com.hayden.proto.prototype.value.NumberContractProto;
import com.hayden.proto.prototype.Permitting;

public sealed interface ResponseCodeContractProto extends ResponseConstructContractProto
    permits
        ResponseCodeContractProto.PermittingNumber {

    record PermittingNumber(DiscreteContractProto<NumberContractProto> permitting)
            implements Permitting<DiscreteContractProto<NumberContractProto>>, ResponseCodeContractProto {}

}
