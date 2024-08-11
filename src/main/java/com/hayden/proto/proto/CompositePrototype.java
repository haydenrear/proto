package com.hayden.proto.proto;

import com.hayden.proto.ty.CompositePrototyped;
import com.hayden.proto.ty.Prototyped;

import java.util.Optional;

public interface CompositePrototype extends Prototype {

    default <P extends Prototype> Optional<P> field(String propertyName) {
        return Optional.empty();
    }

    default <PR extends Prototyped<P, U>, P extends Prototype, U> Optional<PR> retrieve(P toGet, CompositePrototyped<P> retrieveFrom) {
        return Optional.empty();
    }

}
