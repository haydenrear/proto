package com.hayden.proto.proto;

import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.ty.CompositePrototyped;
import com.hayden.proto.ty.Prototyped;

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
