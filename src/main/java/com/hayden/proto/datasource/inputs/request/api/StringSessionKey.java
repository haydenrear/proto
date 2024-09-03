package com.hayden.proto.datasource.inputs.request.api;

import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.datasource_proto.data.value.StringContractProto;
import com.hayden.proto.datasource_proto.inputs.request.SessionKeyContractProto;
import com.hayden.proto.ty.Prototyped;

public interface StringSessionKey extends Prototyped<SessionKeyContractProto> {
    @Override
    default SessionKeyContractProto proto() {
        return new SessionKeyContractProto.PermitsValueContractProto(new ValueContractProto.PermittingString(StringContractProto.EMPTY));
    }

}
