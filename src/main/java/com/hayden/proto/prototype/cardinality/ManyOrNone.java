package com.hayden.proto.prototype.cardinality;

import com.hayden.proto.prototype.Prototype;

import java.util.Optional;

public interface ManyOrNone<T> extends Prototype {

    Optional<T> ofSingleProto();

}
