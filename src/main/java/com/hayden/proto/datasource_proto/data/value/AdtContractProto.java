package com.hayden.proto.datasource_proto.data.value;

import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.proto.CompositePrototype;
import com.hayden.proto.proto.Prototype;
import org.mvel2.ast.Proto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface AdtContractProto extends CompositePrototype<Prototype> {

    default Plural<? extends Prototype> prototypes() {
        return (Any<Prototype>) () -> new Prototype[0];
    }

    default Map<String, ValueContractProto> fields() {
        return new HashMap<>();
    }

    @Override
    default <P extends Prototype> Optional<P> field(String propertyName) {
        try {
            return Optional.ofNullable((P) fields().get(propertyName));
        } catch (ClassCastException c) {
            return Optional.empty();
        }
    }
}
