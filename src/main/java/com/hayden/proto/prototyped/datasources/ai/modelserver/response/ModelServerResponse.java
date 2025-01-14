package com.hayden.proto.prototyped.datasources.ai.modelserver.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerCodingAiClient;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerResponseContract;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ContextRequest;
import com.hayden.proto.prototyped.sources.client.Response;
import lombok.Builder;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@Response(proto = ModelServerResponseContract.class)
public sealed interface ModelServerResponse
        permits
        ModelServerResponse.AddContextResponse,
        ModelServerResponse.ModelServerCodeResponse,
        ModelServerResponse.RetrievedContextResponse {


    @Builder
    record RetrievedContextResponse(List<ContextResponse> contextRequests) implements ModelServerResponse {
    }

    @Builder
    record AddContextResponse(List<ContextRequest> contextRequests) implements ModelServerResponse {
    }

    @Builder
    record ModelServerCodeResponse(ModelServerCodingAiClient.CodeResult codeResult) implements ModelServerResponse {
    }

}
