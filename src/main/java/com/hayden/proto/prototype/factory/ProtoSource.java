package com.hayden.proto.prototype.factory;

import com.hayden.proto.prototype.Prototype;

public interface ProtoSource<P extends Prototype> {

    P build() ;

}
