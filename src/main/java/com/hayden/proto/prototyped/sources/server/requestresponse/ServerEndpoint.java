package com.hayden.proto.prototyped.sources.server.requestresponse;


import com.hayden.proto.prototype.datasource.data.inputs.HeadersContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.PathContractProto;
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.proto.prototyped.sources.data.inputs.request.Path;
import com.hayden.proto.prototyped.sources.data.inputs.request.Query;
import com.hayden.proto.prototyped.sources.data.inputs.request.rest.RestQueryParams;
import com.hayden.proto.prototyped.sources.requestresponse.Headers;
import com.hayden.proto.prototyped.sources.server.stream.MessageStream;
import org.springframework.core.annotation.AliasFor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Inherited;

@Inherited
@Path
@RestQueryParams
@GraphQlEndpoint
@Body
//@RequestMapping
public @interface ServerEndpoint {

    @AliasFor(annotation = Path.class,
              value = "proto")
    Class<? extends PathContractProto>[] pathProto() default {};

    @AliasFor(annotation = Body.class,
              value = "proto")
    Class<? extends PathContractProto>[] requestBody() default {};

    @AliasFor(annotation = Body.class,
              value = "proto")
    Class<? extends PathContractProto>[] responseBody() default {};

    @AliasFor(annotation = RestQueryParams.class,
              value = "protos")
    Query[] queries() default {};

    @AliasFor(annotation = Headers.class,
              value = "proto")
    Class<? extends HeadersContractProto>[] headersProto() default {};

//    @AliasFor(annotation = RequestMapping.class,
//              value = "params")
//    String[] restParams() default {};
//
//    @AliasFor(annotation = RequestMapping.class,
//              value = "headers")
//    String[] restHeaders() default {};
//
//    @AliasFor(annotation = RequestMapping.class,
//              value = "method")
//    RequestMethod[] restMethod() default {};
//
//    @AliasFor(annotation = RequestMapping.class,
//              value = "path")
//    String[] path() default {};
//
//    @AliasFor(annotation = RequestMapping.class,
//              value = "consumes")
//    String[] consumes() default {};
//
//    @AliasFor(annotation = RequestMapping.class,
//              value = "produces")
//    String[] produces() default {};


}
