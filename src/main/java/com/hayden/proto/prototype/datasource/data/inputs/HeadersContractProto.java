package com.hayden.proto.prototype.datasource.data.inputs;

import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.KeyValueContractProto;
import com.hayden.proto.prototype.datasource.data.MultiValueKeyValueContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestHeaderContractProto;

public interface HeadersContractProto<KV extends KeyValueContractProto<MK, MV>, MV extends ValueContractProto, MK extends KeyContractProto, K extends KeyContractProto>
        extends
            MultiValueKeyValueContractProto<KV, MV, MK, K> {

    interface HeadersKeyValueContract
            extends HeadersContractProto<KeyValueContract, ValueContractProto, KeyContractProto, KeyContractProto> {

        ValueContractProto.PermittingKeyValueContract permittingKeyValueContract();

        @Override
        default ValueContractProto.PermittingKeyValue<KeyValueContract, KeyContractProto, ValueContractProto> value() {
            return new ValueContractProto.PermittingKeyValue<>(this.permittingKeyValueContract().permitting());
        }
    }

}
