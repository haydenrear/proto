package com.hayden.proto.prototype.datasource.data;

import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.patterns.PatternContractProto;
import com.hayden.proto.prototype.value.*;
import com.hayden.proto.prototype.Permitting;
import com.hayden.proto.prototype.Prototype;

/**
 * Of the most important embedding - the map key
 */
public sealed interface KeyContractProto extends Prototype
        permits
            KeyContractProto.PermittingByte,
            KeyContractProto.PermittingPattern,
            KeyContractProto.PermittingDiscrete,
            KeyContractProto.PermittingNumber,
            KeyContractProto.PermittingString,
            KeyContractProto.PermittingLiteral,
            KeyContractProto.PermittingPluralKey {

    record PermittingPattern(PatternContractProto permitting)
            implements KeyContractProto, Permitting<PatternContractProto> {}

    record PermittingDiscrete<T>(DiscreteContractProto<T> permitting)
            implements KeyContractProto, Permitting<DiscreteContractProto<T>> {}

    record PermittingLiteral<T extends LiteralContractProto<U>, U>(T permitting)
            implements KeyContractProto, Permitting<T> {}

    record PermittingByte(ByteChunkContractProto permitting)
            implements KeyContractProto, Permitting<ByteChunkContractProto> {}

    record PermittingNumber(
            NumberContractProto permitting)
            implements KeyContractProto, Permitting<NumberContractProto> {}

    record PermittingString(StringContractProto permitting)
            implements KeyContractProto, Permitting<StringContractProto> {}

    record PermittingPluralKey<T extends Plural<KeyContractProto>>(T permitting)
            implements KeyContractProto, Permitting<T> {}


}
