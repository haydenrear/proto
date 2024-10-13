package com.hayden.proto.prototype.datasource.data;

/**
 * @param <KV> multi value key-value contract
 * @param <MV> multi value value conrtract
 * @param <MK> multi value key contract
 * @param <K> key contract pointing to key-value
 */
public interface MultiValueKeyValueContractProto<KV extends KeyValueContractProto<MK, MV>, MV extends ValueContractProto, MK extends KeyContractProto, K extends KeyContractProto>
        extends
            KeyValueContractProto<K, ValueContractProto.PermittingKeyValue<KV, MK, MV>> {


}
