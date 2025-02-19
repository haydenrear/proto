package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DelegatingModelServerResponseDeser {

    List<ModelServerResponseDeser<? extends ModelServerResponse>> responses;

    public Result<? extends ModelServerResponse, DataSourceClient.Err> deserialize(String toParser)  {
        Optional<ModelServerResponseDeser<? extends ModelServerResponse>> first = this.responses.stream()
                .filter(modelServerResponseDeser -> modelServerResponseDeser.matches(toParser))
                .findFirst();
        if (first.isPresent()) {
            ModelServerResponseDeser<? extends ModelServerResponse> modelServerResponseModelServerResponseDeser = first.get();
            Result<ModelServerResponse, DataSourceClient.Err> deserialize = modelServerResponseModelServerResponseDeser.deserialize(toParser)
                    .map(m -> m);
            return deserialize;
        } else {
            return Result.err(new DataSourceClient.Err("Could not find model server deser for %s".formatted(toParser)));
        }
    }


}
