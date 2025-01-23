package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest;
import io.modelcontextprotocol.kotlin.sdk.Implementation;

import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
public interface ModelContextProtocolContextRequest {

    Optional<Implementation> toImplementation();

    Optional<CallToolRequest> toCallToolRequest();

    record ExternalModelContextProtocolContextRequest(Implementation implementation,
                                                      CallToolRequest callToolRequest)
            implements ModelContextProtocolContextRequest {
        @Override
        public Optional<Implementation> toImplementation() {
            return Optional.ofNullable(implementation);
        }

        @Override
        public Optional<CallToolRequest> toCallToolRequest() {
            return Optional.ofNullable(callToolRequest);
        }
    }

}
