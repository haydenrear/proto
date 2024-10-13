package com.hayden.proto.prototype;

import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototyped.CompositePrototyped;
import com.hayden.proto.prototyped.Prototyped;

import java.util.Optional;

public interface CompositePrototype<SUB_P extends Prototype> extends Prototype {

    Plural<? extends SUB_P> prototypes();

    default <P extends Prototype> Optional<P> field(String propertyName) {
        return Optional.empty();
    }

    default <PR extends Prototyped<P>, P extends Prototype> Optional<PR> retrieve(P toGet, CompositePrototyped<P> retrieveFrom) {
        return Optional.empty();
    }

}
