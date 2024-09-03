package com.hayden.proto.datasource.inputs.request;

import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.inputs.request.RequestContractProto;
import com.hayden.proto.proto.Prototype;
import com.hayden.proto.ty.CompositePrototyped;

public interface Request extends CompositePrototyped<RequestContractProto> {

    default Plural<? extends Prototype> contracts() {
        return this.proto().requestContracts();
    }
}
