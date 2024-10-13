package com.hayden.proto.prototyped.sources.client;

import com.hayden.proto.prototype.datasource.data.response.ResponseContractProto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Response {

    /**
     * The user annotates their request with prototype used for describing.
     * @return
     */
    Class<? extends ResponseContractProto>[] proto() default {};

    /**
     * The user provides example request to generate prototypes from. Should be annotated with @Response
     */
    Class<?>[] request() default {};

}
