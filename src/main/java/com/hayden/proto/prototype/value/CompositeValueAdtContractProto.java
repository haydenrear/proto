package com.hayden.proto.prototype.value;

import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.Prototype;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface CompositeValueAdtContractProto extends AdtContractProto {

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
