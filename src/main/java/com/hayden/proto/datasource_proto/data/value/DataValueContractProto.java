package com.hayden.proto.datasource_proto.data.value;

import com.hayden.proto.datasource_proto.data.KeyContractProto;
import com.hayden.proto.datasource_proto.data.KeyValueContractProto;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.permitting.Permitting;


public sealed interface DataValueContractProto
        permits DataValueContractProto.PermittingKeyValueContractProto,
                DataValueContractProto.PermittingValueContractProto {


    record PermittingKeyValueContractProto<K extends KeyContractProto, V extends ValueContractProto>(KeyValueContractProto<K, V> permitting)
            implements Permitting<KeyValueContractProto<K, V>>, DataValueContractProto {}

    record PermittingValueContractProto(ValueContractProto permitting)
            implements Permitting<ValueContractProto>, DataValueContractProto {}

}
