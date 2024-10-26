package com.hayden.proto.prototyped.sources.server.queue;

import com.hayden.proto.prototype.datasource.data.inputs.request.RequestContractProto;

public @interface QueueProducer {

    Class<? extends RequestContractProto>[] prototype() default {};

}
