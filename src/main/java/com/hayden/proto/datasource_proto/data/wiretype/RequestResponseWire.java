package com.hayden.proto.datasource_proto.data.wiretype;

public interface RequestResponseWire<REQ_WIRE extends WireContractProto, RES_WIRE extends WireContractProto> {

    REQ_WIRE res();
    RES_WIRE req();

}
