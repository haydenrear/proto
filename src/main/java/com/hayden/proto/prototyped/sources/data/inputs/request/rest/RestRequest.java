package com.hayden.proto.prototyped.sources.data.inputs.request.rest;

import com.hayden.proto.prototyped.sources.data.inputs.request.*;

public @interface RestRequest {

    Request[] requests() default {};
    Path[] path() default {};
    Host[] host() default {};
    Protocol[] protocol() default {};
    RestQueryParams[] query() default {};
    Body[] body() default {};
    Limit[] limit() default {};

}
