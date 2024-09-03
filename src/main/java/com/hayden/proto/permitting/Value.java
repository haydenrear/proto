package com.hayden.proto.permitting;

import com.hayden.proto.proto.Prototype;
import com.hayden.proto.ty.Prototyped;

public interface Value<T, P extends Prototype> extends Prototyped<P> {

    T value();

    default P proto() {
        if (this instanceof Prototype p) {
            return (P) this;
        }
        throw new RuntimeException("Must override proto if Value is not self prototype.");
    }
}
