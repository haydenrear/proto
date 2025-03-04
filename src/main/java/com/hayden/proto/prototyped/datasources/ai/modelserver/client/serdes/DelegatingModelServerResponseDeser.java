package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DelegatingModelServerResponseDeser {

    List<ModelServerResponseDeser<? extends ModelServerResponse>> responses;

    public Result<? extends ModelServerResponse, DataSourceClient.Err> deserialize(String toParser)  {
        var first = this.responses.stream()
                .flatMap(modelServerResponseDeser -> modelServerResponseDeser.matches(toParser)
                        .map(toParse -> Map.entry(toParse, modelServerResponseDeser))
                        .stream())
                .findFirst();
        if (first.isPresent()) {
            var modelServerResponseModelServerResponseDeser = first.get();
            Result<ModelServerResponse, DataSourceClient.Err> deserialize = modelServerResponseModelServerResponseDeser.getValue().deserialize(modelServerResponseModelServerResponseDeser.getKey())
                    .map(e -> e);
            return deserialize;
        } else {
            return Result.err(new DataSourceClient.Err("Could not find model server deser for %s".formatted(toParser)));
        }
    }


}
