package com.hayden.proto.datasource_proto.data;

import com.hayden.proto.datasource_proto.data.patterns.PatternContractProto;
import com.hayden.proto.datasource_proto.data.value.*;
import com.hayden.proto.datasource_proto.data.value.ByteChunkContractProto;
import com.hayden.proto.datasource_proto.data.value.DiscreteContractProto;
import com.hayden.proto.datasource_proto.data.value.NumberContractProto;
import com.hayden.proto.datasource_proto.data.value.StringContractProto;
import com.hayden.proto.permitting.Permitting;
import com.hayden.proto.proto.Prototype;

public sealed interface KeyContractProto extends Prototype
        permits
            KeyContractProto.PermittingByte,
            KeyContractProto.PermittingPattern,
            KeyContractProto.PermittingDiscrete,
            KeyContractProto.PermittingNumber,
            KeyContractProto.PermittingString,
            KeyContractProto.PermittingLiteral {

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


}
