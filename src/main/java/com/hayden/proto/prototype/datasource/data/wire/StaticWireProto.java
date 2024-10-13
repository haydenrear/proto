package com.hayden.proto.prototype.datasource.data.wire;

import com.hayden.proto.prototype.Prototype;

public non-sealed interface StaticWireProto
        extends Prototype, WireContractProto {

    StaticWireProto EMPTY = new StaticWireProto() {};

}
