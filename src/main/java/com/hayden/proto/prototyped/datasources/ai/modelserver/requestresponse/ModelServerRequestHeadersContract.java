package com.hayden.proto.prototyped.datasources.ai.modelserver.requestresponse;


import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.HeadersContractProto;

public record ModelServerRequestHeadersContract(ValueContractProto.PermittingKeyValueContract permittingKeyValueContract,
                                                KeyContractProto key)
        implements HeadersContractProto.HeadersKeyValueContract {

}
