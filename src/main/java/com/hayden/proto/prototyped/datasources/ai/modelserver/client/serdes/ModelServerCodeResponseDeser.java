package com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public interface ModelServerCodeResponseDeser<T> extends ModelServerResponseDeser<ModelServerResponse.ModelServerCodeResponse<T>> {

    Logger log = LoggerFactory.getLogger(ModelServerCodeResponseDeser.class);

    static Optional<String> matchesCode(String matcher, ObjectMapper om) {
        return ModelServerResponseDeser.matchesCode(matcher, om, "code");
    }

}
