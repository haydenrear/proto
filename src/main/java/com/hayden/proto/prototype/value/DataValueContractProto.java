package com.hayden.proto.prototype.value;

import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.KeyValueContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.Permitting;


public sealed interface DataValueContractProto
        permits DataValueContractProto.PermittingKeyValueContractProto,
                DataValueContractProto.PermittingValueContractProto {


    record PermittingKeyValueContractProto<K extends KeyContractProto, V extends ValueContractProto>(KeyValueContractProto<K, V> permitting)
            implements Permitting<KeyValueContractProto<K, V>>, DataValueContractProto {}

    record PermittingValueContractProto(ValueContractProto permitting)
            implements Permitting<ValueContractProto>, DataValueContractProto {}

}
