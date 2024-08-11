package com.hayden.proto.datasource.inputs.request.rest;

import com.hayden.proto.datasource.inputs.request.Protocol;
import com.hayden.proto.datasource_proto.inputs.request.ProtocolContractProto;

public record RestProtocol(String wrapped) implements Protocol {

    @Override
    public ProtocolContractProto proto() {
        return new ProtocolContractProto.PermitsDiscretePatternProto(() -> new String[] {"http", "https"});
    }

}
