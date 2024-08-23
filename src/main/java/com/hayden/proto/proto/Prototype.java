package com.hayden.proto.proto;

import com.hayden.proto.ty.Prototyped;

public interface Prototype {

    default <T> boolean is(Prototyped<? extends Prototype, T> validate) {
        return false;
    }

    default <T> T retrieve(Prototyped<? extends Prototype, T> validate) {
        throw new RuntimeException("Did not implement");
    }

}
