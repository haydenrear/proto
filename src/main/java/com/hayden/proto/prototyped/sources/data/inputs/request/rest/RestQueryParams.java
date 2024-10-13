package com.hayden.proto.prototyped.sources.data.inputs.request.rest;

import com.hayden.proto.prototyped.sources.data.inputs.request.Query;

public @interface RestQueryParams {

    Query[] protos() default {};

}
