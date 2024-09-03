package com.hayden.proto.datasource.inputs.request;

import com.hayden.proto.datasource_proto.inputs.request.BodyContractProto;
import com.hayden.proto.proto.DelegatingPrototype;
import com.hayden.proto.ty.Prototyped;

public interface Body<U> extends Prototyped<DelegatingPrototype<BodyContractProto>> {

    U wrapped() ;

}