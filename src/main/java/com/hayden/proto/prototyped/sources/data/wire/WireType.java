package com.hayden.proto.prototyped.sources.data.wire;

import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public @interface WireType {

    record FluxWireType<T>(Flux<T> t) {}

    record StreamWireType<T>(Stream<T> t) {}

    record IterableRecordType<T>(Iterable<T> t) {}

}
