package com.hayden.proto.datasource_proto.cardinality;

import com.hayden.proto.proto.Prototype;

public interface Many<T> extends Prototype {

    T ofSingleProto();

}
