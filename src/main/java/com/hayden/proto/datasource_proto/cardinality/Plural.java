package com.hayden.proto.datasource_proto.cardinality;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public interface Plural<T> {

    Stream<T> pluralize();

    default <U extends T> Optional<U> retrieve(Class<U> clazz) {
        return pluralize().filter(clazz::isInstance).map(clazz::cast)
                .findAny();
    }

}
