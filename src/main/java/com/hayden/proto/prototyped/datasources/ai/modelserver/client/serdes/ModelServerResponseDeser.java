package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.SingleError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ModelServerResponseDeser {

    private final ObjectMapper objectMapper;

    public Result<ModelServerResponse, DataSourceClient.DataSourceClientPrototypeError> deserialize(String toParser) {

        IOException exc = null;
        try {
            return Result.ok(objectMapper.readValue(toParser, ModelServerResponse.class));
        } catch (IOException e) {
            exc = e;
        }

        return Result.err(new DataSourceClient.DataSourceClientPrototypeError("Could not parse model server response\n%s."
                .formatted(SingleError.parseStackTraceToString(exc))));

    }
}
