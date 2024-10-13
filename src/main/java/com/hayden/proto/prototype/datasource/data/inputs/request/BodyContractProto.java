package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.value.ByteChunkContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.search.SearchContractProto;
import com.hayden.proto.prototype.Permitting;

public interface BodyContractProto
        extends
                RequestConstructContractProto {

    record PermitsSearchContractProto(SearchContractProto permitting)
            implements Permitting<SearchContractProto>, BodyContractProto{}
    record PermittingByte(ByteChunkContractProto permitting)
            implements Permitting<ByteChunkContractProto>, BodyContractProto{}

}