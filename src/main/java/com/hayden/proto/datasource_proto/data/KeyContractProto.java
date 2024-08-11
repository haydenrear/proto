package com.hayden.proto.datasource_proto.data;

import com.hayden.proto.datasource_proto.data.patterns.PatternContractProto;
import com.hayden.proto.datasource_proto.data.value.*;
import com.hayden.proto.datasource_proto.data.value.ByteChunkContractProto;
import com.hayden.proto.datasource_proto.data.value.DiscreteContractProto;
import com.hayden.proto.datasource_proto.data.value.NumberContractProto;
import com.hayden.proto.datasource_proto.data.value.StringContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface KeyContractProto
        permits
            KeyContractProto.PermittingByte,
            KeyContractProto.PermittingPattern,
            KeyContractProto.PermittingDiscrete,
            KeyContractProto.PermittingNumber,
            KeyContractProto.PermittingString {

    record PermittingPattern(PatternContractProto permitting)
            implements KeyContractProto, Permitting<PatternContractProto> {}

    record PermittingDiscrete(
            DiscreteContractProto permitting)
            implements KeyContractProto, Permitting<DiscreteContractProto> {}

    record PermittingByte(
            ByteChunkContractProto permitting)
            implements KeyContractProto, Permitting<ByteChunkContractProto> {}

    record PermittingNumber(
            NumberContractProto permitting)
            implements KeyContractProto, Permitting<NumberContractProto> {}

    record PermittingString(
            StringContractProto permitting)
            implements KeyContractProto, Permitting<StringContractProto> {}


}
