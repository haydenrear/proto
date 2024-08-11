package com.hayden.proto.datasource_proto.cardinality;

import java.util.Arrays;
import java.util.Optional;

public interface Any<T> {

    T[] ofMany();

    default <U extends T> Optional<U> retrieve(Class<U> clazz) {
        return Arrays.stream(ofMany()).filter(clazz::isInstance).map(clazz::cast)
                .findAny();
    }

}
