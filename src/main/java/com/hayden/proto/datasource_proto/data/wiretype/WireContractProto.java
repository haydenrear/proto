package com.hayden.proto.datasource_proto.data.wiretype;

import com.hayden.proto.proto.Prototype;

public sealed interface WireContractProto extends Prototype
    permits
        StreamingWireProto,
        StaticWireProto { }
