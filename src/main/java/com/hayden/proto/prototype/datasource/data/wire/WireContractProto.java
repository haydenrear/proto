package com.hayden.proto.prototype.datasource.data.wire;

import com.hayden.proto.prototype.Prototype;

public sealed interface WireContractProto extends Prototype
    permits
        StreamingWireProto,
        StaticWireProto { }
