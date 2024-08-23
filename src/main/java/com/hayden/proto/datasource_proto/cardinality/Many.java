package com.hayden.proto.datasource_proto.cardinality;

import com.hayden.proto.proto.Prototype;

import java.util.stream.Stream;

public interface Many<T> extends Prototype, Plural<T> {

    T ofSingleProto();

    @Override
    default Stream<T> pluralize() {
        return Stream.of(ofSingleProto());
    }
}
