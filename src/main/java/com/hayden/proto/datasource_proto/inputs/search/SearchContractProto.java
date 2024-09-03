package com.hayden.proto.datasource_proto.inputs.search;

import com.hayden.proto.datasource_proto.data.KeyContractProto;
import com.hayden.proto.datasource_proto.data.KeyValueContractProto;
import com.hayden.proto.datasource_proto.data.MultiValueKeyValueContractProto;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.permitting.Permitting;
import com.hayden.proto.proto.Prototype;

public sealed interface SearchContractProto extends Prototype
    permits
        SearchContractProto.PermittingKeyValue,
        SearchContractProto.PermittingValue,
        SearchContractProto.PermittingMultiValue {

    record PermittingMultiValue<
            K extends KeyContractProto,
            MK extends KeyContractProto,
            MV extends ValueContractProto,
            KV extends KeyValueContractProto<MK, MV>>(
            MultiValueKeyValueContractProto<KV, MV, MK, K> permitting
    ) implements
            Permitting<MultiValueKeyValueContractProto<KV, MV, MK, K>>,
            SearchContractProto { }

    record PermittingKeyValue(KeyValueContractProto<KeyContractProto, ValueContractProto> permitting)
            implements Permitting<KeyValueContractProto<KeyContractProto, ValueContractProto>>, SearchContractProto {}
    record PermittingValue(ValueContractProto permitting)
            implements Permitting<ValueContractProto>, SearchContractProto {}

}
