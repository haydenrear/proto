package com.hayden.proto.prototyped.sources.server;


import com.hayden.proto.prototype.datasource.server.ServerEndpointContract;

public @interface ServerEndpoint {

    Class<? extends ServerEndpointContract>[] proto() default {};

}
