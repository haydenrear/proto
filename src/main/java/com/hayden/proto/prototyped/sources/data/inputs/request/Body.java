package com.hayden.proto.prototyped.sources.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.inputs.request.BodyContractProto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Body{

    Class<? extends BodyContractProto>[] proto() default {};

    Class<?>[] body() default {};

}