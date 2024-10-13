package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.wire.WireContractProto;
import com.hayden.proto.prototype.CompositePrototype;

public interface RequestContractProto
        extends CompositePrototype<RequestConstructContractProto> {

    default Plural<? extends RequestConstructContractProto> prototypes() {
        return requestContracts();
    }

    Plural<? extends RequestConstructContractProto> requestContracts();

    default WireTypeRequestContractProto wire() {
        return new WireTypeRequestContractProto() {
            @Override
            public WireContractProto contract() {
                return null;
            }
        };
    }


}
