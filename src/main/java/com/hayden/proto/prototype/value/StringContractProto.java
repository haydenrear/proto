package com.hayden.proto.prototype.value;

import com.hayden.proto.prototype.cardinality.All;
import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.CompositePrototype;

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

    default Plural<? extends StringContractItemProto> prototypes() {
        throw new RuntimeException("Not implemented");
    }

    default All<? extends StringContractItemProto> all() {
        return () -> new StringContractItemProto[0];
    }

    default Any<? extends StringContractItemProto> any() {
        return () -> new StringContractItemProto[0];
    }
}
