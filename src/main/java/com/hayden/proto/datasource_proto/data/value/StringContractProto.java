package com.hayden.proto.datasource_proto.data.value;

import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.proto.CompositePrototype;
import com.hayden.proto.proto.Prototype;

public interface StringContractProto extends CompositePrototype<StringContractProto.StringContractItemProto> {

    StringContractProto EMPTY = new StringContractProto() {};

    static StringContractProto createStringContractProto(StringContractProto.StringContractItemProto[] protos) {
        return new StringContractProto() {
            @Override
            public Any<StringContractItemProto> prototypes() {
                return () -> protos;
            }
        };
    }

    interface StringContractItemProto extends ContractItem {

        ContractItem contract();

        record MaxLengthString(MaxLength contract) implements StringContractItemProto {}

        record NullableString(Nullable contract) implements StringContractItemProto {}

    }

    default Any<? extends StringContractItemProto> prototypes() {
        return () -> new StringContractItemProto[0];
    }
}
