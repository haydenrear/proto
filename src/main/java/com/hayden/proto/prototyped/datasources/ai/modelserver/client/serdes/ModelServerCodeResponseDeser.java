package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public interface ModelServerCodeResponseDeser<T> extends ModelServerResponseDeser<ModelServerResponse.ModelServerCodeResponse<T>> {

    Logger log = LoggerFactory.getLogger(ModelServerCodeResponseDeser.class);

    static boolean matchesCode(String matcher, ObjectMapper om) {
        try {
            var m = om.readValue(matcher, new TypeReference<Map<String, Object>>() {});
            return Objects.equals(m.get("type"), "code");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }


}
