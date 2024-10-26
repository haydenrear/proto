package com.hayden.proto.prototyped.sources.server.queue;

public @interface Queue {

    QueueConsumer[] consumer() default  {};

    QueueProducer[] producer() default  {};

}
