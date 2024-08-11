package com.hayden.proto.datasource.inputs.search;

import com.hayden.proto.datasource_proto.data.KeyContractProto;
import com.hayden.proto.datasource_proto.data.KeyValueContractProto;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface Search
    permits
        Search.PermittingKeyValueContractProto,
        Search.PermittingValueContractProto {

    record PermittingKeyValueContractProto(KeyValueContractProto<KeyContractProto, ValueContractProto> permitting)
            implements Permitting<KeyValueContractProto<KeyContractProto, ValueContractProto>>, Search {}
    record PermittingValueContractProto(ValueContractProto permitting)
            implements Permitting<ValueContractProto>, Search {}

}
