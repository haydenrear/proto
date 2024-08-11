package com.hayden.proto.ty;

import com.hayden.proto.proto.Prototype;

import java.util.Optional;

public interface CompositePrototypedBehavior<P extends Prototype> extends Prototyped<P, P> {

    @Override
    default P wrapped() {
        return this.proto();
    }

    default <PR extends Prototype> Optional<PR> retrieve(Class<PR> prototype) {
        return Optional.empty();
    }

}
