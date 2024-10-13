package com.hayden.proto.prototype.cardinality;

import com.hayden.proto.prototype.Prototype;

import java.util.Optional;

public interface May<T> extends Prototype {

    Optional<T> have();

}
