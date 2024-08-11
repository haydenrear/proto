package com.hayden.proto.datasource_proto.inputs.search;

import com.hayden.proto.datasource_proto.data.KeyContractProto;
import com.hayden.proto.datasource_proto.data.KeyValueContractProto;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.permitting.Permitting;
import com.hayden.proto.proto.Prototype;

public sealed interface SearchContractProto extends Prototype
    permits
        SearchContractProto.PermittingKeyValueContractProto,
        SearchContractProto.PermittingValueContractProto {

    record PermittingKeyValueContractProto(KeyValueContractProto<KeyContractProto, ValueContractProto> permitting)
            implements Permitting<KeyValueContractProto<KeyContractProto, ValueContractProto>>, SearchContractProto {}
    record PermittingValueContractProto(ValueContractProto permitting)
            implements Permitting<ValueContractProto>, SearchContractProto {}

}
