package com.hayden.proto.prototype.datasource.data;

import com.hayden.proto.prototype.cardinality.Many;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.patterns.PatternContractProto;
import com.hayden.proto.prototype.Permitting;
import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.value.*;

/**
 * Of the most important - the map value
 */
public sealed interface ValueContractProto extends Prototype
        permits
            ValueContractProto.PermittingByte,
            ValueContractProto.PermittingPattern,
            ValueContractProto.PermittingDiscrete,
            ValueContractProto.PermittingAdt,
            ValueContractProto.PermittingNumber,
            ValueContractProto.PermittingString,
            ValueContractProto.PermittingManyValues,
            ValueContractProto.PermittingKeyValue,
            ValueContractProto.PermittingKeyValueContract,
            ValueContractProto.PermittingAnyValue,
            ValueContractProto.PermittingPluralValueContract {

    record PermittingManyValues<V extends ValueContractProto>(Many<V> permitting)
            implements ValueContractProto, Permitting<Many<V>> {}
    record PermittingPattern(PatternContractProto permitting)
            implements ValueContractProto, Permitting<PatternContractProto> {}
    record PermittingDiscrete<T extends Prototype>(DiscreteContractProto<T> permitting)
            implements ValueContractProto, Permitting<DiscreteContractProto<T>> {}
    record PermittingByte(ByteChunkContractProto permitting)
            implements ValueContractProto, Permitting<ByteChunkContractProto> {}
    record PermittingNumber(NumberContractProto permitting)
            implements ValueContractProto, Permitting<NumberContractProto> {}
    record PermittingString(StringContractProto permitting)
            implements ValueContractProto, Permitting<StringContractProto> {}
    record PermittingAdt(AdtContractProto permitting)
            implements ValueContractProto, Permitting<AdtContractProto> {}
    record PermittingKeyValue<KV extends KeyValueContractProto<K, V>, K extends KeyContractProto, V extends ValueContractProto>(KV permitting)
            implements ValueContractProto, Permitting<KV> {}
    record PermittingKeyValueContract(KeyValueContractProto.KeyValueContract permitting)
            implements ValueContractProto, Permitting<KeyValueContractProto.KeyValueContract> {}
    record PermittingPluralValueContract<T extends Plural<ValueContractProto>>(T permitting)
            implements ValueContractProto, Permitting<T> {}
    record PermittingAnyValue()
            implements ValueContractProto {}

}