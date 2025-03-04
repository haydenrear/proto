package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.proto.prototyped.sources.client.DataSourceClient;
import com.hayden.utilitymodule.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public interface ModelServerResponseDeser<T extends ModelServerResponse> {

    Logger log = LoggerFactory.getLogger(ModelServerResponseDeser.class);

    static Optional<String> matchesCode(String matcher, ObjectMapper om, String typeValue) {
        try {
            var m = om.readValue(matcher, new TypeReference<Map<String, Object>>() {});
            if (m.containsKey("data")) {
                var data = (Map<String, Object>) m.get("data");
                Object o = data.get("type");
                if (o != null && o.toString().startsWith(typeValue)) {
                    data.put("type", typeValue);
                    var written = Optional.of(om.writeValueAsString(data));
                    return written;
                }
            } else {
                Object o = m.get("type");
                if (o != null && o.toString().startsWith(typeValue)) {
                    m.put("type", typeValue);
                    var written = Optional.of(om.writeValueAsString(m));
                    return written;
                }
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    Result<T, DataSourceClient.Err> deserialize(String toParser) ;

    Optional<String> matches(String matcher);

}
