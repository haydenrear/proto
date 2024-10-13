package com.hayden.proto.prototype.datasource.data.inputs.search;

import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.KeyValueContractProto;
import com.hayden.proto.prototype.datasource.data.MultiValueKeyValueContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.Permitting;
import com.hayden.proto.prototype.Prototype;

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
