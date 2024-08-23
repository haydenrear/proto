package com.hayden.proto.datasource_proto.cardinality;

import java.util.Arrays;
import java.util.stream.Stream;

public interface ManyOf<T> extends Plural<T> {

    T[] of();

    @Override
    default Stream<T> pluralize() {
        return Arrays.stream(of()) ;
    }
}
