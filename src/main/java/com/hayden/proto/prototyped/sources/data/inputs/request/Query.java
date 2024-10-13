package com.hayden.proto.prototyped.sources.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.inputs.request.QueryContractProto;

public @interface Query {

    Class<? extends QueryContractProto>[] proto() default {};

}