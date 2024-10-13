package com.hayden.proto.prototype;

public interface DelegatingPrototype<P extends Prototype> extends Prototype {

    P delegator();

}
