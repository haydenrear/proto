package com.hayden.proto.prototyped.sources.server.stream;

import com.hayden.proto.prototype.datasource.data.inputs.request.PathContractProto;
import com.hayden.proto.prototyped.sources.data.inputs.request.Path;
import org.springframework.core.annotation.AliasFor;
import org.springframework.messaging.handler.annotation.MessageMapping;

@MessageMapping
@Path
public @interface MessageStream {

    @AliasFor(annotation = MessageMapping.class,
              value = "value")
    String[] socketEndpoint() default {};

    @AliasFor(annotation = Path.class,
              value = "proto")
    Class<? extends PathContractProto>[] pathProto() default {};

}
