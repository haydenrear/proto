package com.hayden.proto.prototype.datasource.data;

import com.hayden.proto.prototype.Prototype;

/**
 * Of the most important - the key and value
 * @param <K>
 * @param <V>
 */
public interface KeyValueContractProto<K extends KeyContractProto, V extends ValueContractProto> extends Prototype {

    K key();

    V value();

    interface KeyValueContract extends KeyValueContractProto<KeyContractProto, ValueContractProto> {}

}
