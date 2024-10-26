package com.hayden.proto.prototyped.sources.server.queue;

import com.hayden.proto.prototype.datasource.data.response.ResponseContractProto;

public @interface QueueConsumer {


    Class<? extends ResponseContractProto>[] prototype() default {};

}
