package com.hayden.proto.datasource_proto.data.value;

import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.proto.CompositePrototype;
import com.hayden.proto.proto.Prototype;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface AdtContractProto extends CompositePrototype {

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
