package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest;
import io.modelcontextprotocol.kotlin.sdk.Implementation;

import java.util.Optional;

public interface ContextRequest {

    Optional<Implementation> toImplementation();

    Optional<CallToolRequest> toCallToolRequest();

}
