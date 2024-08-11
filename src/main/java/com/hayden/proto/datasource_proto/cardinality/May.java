package com.hayden.proto.datasource_proto.cardinality;

import com.hayden.proto.proto.Prototype;

import java.util.Optional;

public interface May<T> extends Prototype {

    Optional<T> have();

}
