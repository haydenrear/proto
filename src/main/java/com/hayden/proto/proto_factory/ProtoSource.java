package com.hayden.proto.proto_factory;

import com.hayden.proto.proto.Prototype;

public interface ProtoSource<P extends Prototype> {

    P build();

}
