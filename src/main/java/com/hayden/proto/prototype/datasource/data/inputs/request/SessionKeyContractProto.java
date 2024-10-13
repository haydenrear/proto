package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.Permitting;

public sealed interface SessionKeyContractProto extends RequestConstructContractProto
        permits
        SessionKeyContractProto.PermitsValueContractProto {

    record PermitsValueContractProto(ValueContractProto permitting)
            implements Permitting<ValueContractProto>, SessionKeyContractProto {}

}