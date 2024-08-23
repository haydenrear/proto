package com.hayden.proto.datasource_proto.cardinality;

import com.hayden.proto.permitting.Permitting;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public interface Any<T> extends Plural<T> {

    T[] ofMany();

    default Stream<T> pluralize() {
        return Arrays.stream(ofMany());
    }
}
