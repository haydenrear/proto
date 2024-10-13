package com.hayden.proto.prototyped;

import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.Prototype;

public interface CompositePrototypedBehavior<P extends Prototype> extends Prototyped<P> {

    /**
     * These are the contracts that P satisfies, as if P is the delegate
     * @return
     */
    Plural<? extends Prototype> contracts();

}
