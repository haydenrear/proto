package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
public interface ModelServerCodeResponseDeser<T> {

//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private final TypeReference<ModelServerResponse.ModelServerCodeResponse<T>> typeRef;

    Result<ModelServerResponse.ModelServerCodeResponse<T>, DataSourceClient.Err> deserialize(String toParser) ;
//    {
//        try {
//            return Result.ok(objectMapper.readValue(toParser, new TypeReference<ModelServerResponse.ModelServerCodeResponse<T>>() {}));
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to deserialize model server code response", e);
//        }
//    }
}
