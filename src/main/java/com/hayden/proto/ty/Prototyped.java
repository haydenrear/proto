package com.hayden.proto.ty;

import com.hayden.proto.proto.Prototype;

public interface Prototyped<P extends Prototype> {

    P proto();

}
