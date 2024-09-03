package com.hayden.proto.datasource_proto.data;

import com.hayden.proto.proto.Prototype;

public interface KeyValueContractProto<K extends KeyContractProto, V extends ValueContractProto> extends Prototype {

    K key();
    V value();

}
