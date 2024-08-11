package com.hayden.proto.datasource.data.wire;

import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public interface WireType<T, W extends WireContractProto> {

    record FluxWireType<T>(Flux<T> t) {}

    record StreamWireType<T>(Stream<T> t) {}

    record IterableRecordType<T>(Iterable<T> t) {}

}
