package com.hayden.proto.datasource_proto.data;

import com.hayden.proto.datasource_proto.cardinality.Many;
import com.hayden.proto.datasource_proto.data.patterns.PatternContractProto;
import com.hayden.proto.datasource_proto.data.value.*;
import com.hayden.proto.datasource_proto.data.value.*;
import com.hayden.proto.permitting.Permitting;
import com.hayden.proto.proto.Prototype;

public sealed interface ValueContractProto extends Prototype
        permits
            ValueContractProto.PermittingByte,
            ValueContractProto.PermittingPattern,
            ValueContractProto.PermittingDiscrete,
            ValueContractProto.PermittingAdt,
            ValueContractProto.PermittingNumber,
            ValueContractProto.PermittingString,
            ValueContractProto.PermittingManyValues,
            ValueContractProto.PermittingKeyValue
{

    record PermittingManyValues<V extends ValueContractProto>(Many<V> permitting)
            implements ValueContractProto, Permitting<Many<V>> {}
    record PermittingPattern(PatternContractProto permitting)
            implements ValueContractProto, Permitting<PatternContractProto> {}
    record PermittingDiscrete<T>(
            DiscreteContractProto<T> permitting)
            implements ValueContractProto, Permitting<DiscreteContractProto<T>> {}
    record PermittingByte(
            ByteChunkContractProto permitting)
            implements ValueContractProto, Permitting<ByteChunkContractProto> {}
    record PermittingNumber(
            NumberContractProto permitting)
            implements ValueContractProto, Permitting<NumberContractProto> {}
    record PermittingString(
            StringContractProto permitting)
            implements ValueContractProto, Permitting<StringContractProto> {}
    record PermittingAdt(
            AdtContractProto permitting)
            implements ValueContractProto, Permitting<AdtContractProto> {}
    record PermittingKeyValue<KV extends KeyValueContractProto<K, V>, K extends KeyContractProto, V extends ValueContractProto>(KV permitting)
            implements ValueContractProto, Permitting<KV> {}

}