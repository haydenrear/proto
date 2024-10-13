package com.hayden.proto.prototyped.sources.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.inputs.request.BodyContractProto;

public @interface Body{

    Class<? extends BodyContractProto>[] prototype() default {};

    Class<?>[] body() default {};

}