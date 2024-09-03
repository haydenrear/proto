package com.hayden.proto.datasource.inputs.request.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.datasource.data.value.JacksonSerializableRequestBody;
import com.hayden.proto.datasource.inputs.request.ByteBody;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.datasource_proto.inputs.request.BodyContractProto;
import com.hayden.proto.datasource_proto.inputs.search.SearchContractProto;

import java.io.IOException;

public record JacksonRequestBody<T>(byte[] wrapped) implements ByteBody {

    @Override
    public BodyContractProto proto() {
        return new BodyContractProto.PermitsSearchContractProto(
                new SearchContractProto.PermittingValue(
                        new ValueContractProto.PermittingAdt(new JacksonSerializableRequestBody())));
    }

    public T serialize(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(this.wrapped, new TypeReference<>() {});
    }

}