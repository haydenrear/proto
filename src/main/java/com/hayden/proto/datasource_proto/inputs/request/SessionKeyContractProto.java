package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface SessionKeyContractProto extends RequestConstructContractProto
        permits
        SessionKeyContractProto.PermitsValueContractProto {

    record PermitsValueContractProto(ValueContractProto permitting)
            implements Permitting<ValueContractProto>, SessionKeyContractProto {}

}