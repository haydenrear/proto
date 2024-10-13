package com.hayden.proto.prototype.cardinality;

import com.hayden.proto.prototype.Prototype;

import java.util.stream.Stream;

public interface Many<T> extends Plural<T> {

    T ofSingleProto();

    @Override
    default Stream<T> pluralize() {
        return Stream.of(ofSingleProto());
    }
}
