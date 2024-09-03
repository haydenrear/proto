package com.hayden.proto.datasource_proto.cardinality;

import java.util.Arrays;
import java.util.stream.Stream;

public interface All<T> extends Plural<T> {

    T[] ofMany();

    default Stream<T> pluralize() {
        return Arrays.stream(ofMany());
    }
}
