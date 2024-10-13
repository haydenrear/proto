package com.hayden.proto.prototype.cardinality;

import com.hayden.proto.prototype.Prototype;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Plural<T> extends Prototype {

    Stream<T> pluralize();

    default <U extends T> Optional<U> retrieve(Class<U> clazz) {
        return pluralize().filter(clazz::isInstance).map(clazz::cast)
                .findAny();
    }

    default <U extends T> List<U> retrieveMany(Class<U> clazz) {
        return pluralize().filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

}
