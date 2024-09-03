package com.hayden.proto.ty;

import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.proto.Prototype;

import java.util.Optional;

public interface CompositePrototypedBehavior<P extends Prototype> extends Prototyped<P> {

    /**
     * These are the contracts that P satisfies, as if P is the delegate
     * @return
     */
    Plural<? extends Prototype> contracts();

    default <PR extends Prototype> Optional<PR> retrieve(Class<PR> prototype) {
        return Optional.empty();
    }

}
