package com.hayden.proto.prototyped.sources.client;

import com.hayden.proto.prototype.datasource.data.inputs.request.RequestContractProto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequestSourceDesc {

    /**
     * The user annotates their request with prototype used for describing.
     * @return
     */
    Class<? extends RequestContractProto>[] proto() default {};

    /**
     * The user provides example request to generate prototypes from. Should be annotated with @Request
     */
    Class<?>[] request() default {};

}
