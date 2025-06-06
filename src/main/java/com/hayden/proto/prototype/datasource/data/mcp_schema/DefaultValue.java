package com.hayden.proto.prototype.datasource.data.mcp_schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.PARAMETER})
public @interface DefaultValue {

    String value();

}
