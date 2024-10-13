package com.hayden.proto.prototyped.sources.requestresponse;

import com.hayden.proto.prototype.datasource.data.inputs.HeadersContractProto;

public @interface Headers {

    Class<? extends HeadersContractProto>[] proto() default {};

}
