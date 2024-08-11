package com.hayden.proto.datasource_proto.data;

public interface KeyValueContractProto<K extends KeyContractProto, V extends ValueContractProto> {

    K key();
    V value();

}
