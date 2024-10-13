package com.hayden.proto.prototyped.sources.client;

import com.hayden.proto.prototype.datasource.client.DataSourceClientContractProto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataClient {

    /**
     * The user annotates their client with prototype used for describing.
     * @return
     */
    Class<? extends DataSourceClientContractProto>[] proto() default {};

    /**
     * The user provides example client to generate prototypes from.
     */
    Class<? extends DataSourceClient>[] client() default {};

}
