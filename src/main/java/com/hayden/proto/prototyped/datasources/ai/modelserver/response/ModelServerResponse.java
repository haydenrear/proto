package com.hayden.proto.prototyped.datasources.ai.modelserver.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerCodingAiClient;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerResponseContract;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelContextProtocolContextRequest;
import com.hayden.proto.prototyped.sources.client.Response;
import lombok.Builder;

import java.util.List;

@Response(proto = ModelServerResponseContract.class)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ModelServerResponse.AddContextResponse.class, name = "add_context"),
        @JsonSubTypes.Type(value = ModelServerResponse.ModelServerCodeResponse.class, name = "code"),
        @JsonSubTypes.Type(value = ModelServerResponse.RetrievedContextResponse.class, name = "retrieved_context")
})
public sealed interface ModelServerResponse
        permits
            ModelServerResponse.AddContextResponse,
            ModelServerResponse.ModelServerCodeResponse,
            ModelServerResponse.RetrievedContextResponse {

    @Builder
    record RetrievedContextResponse(List<ContextResponse> contextRequests)
            implements ModelServerResponse {}

    @Builder
    record AddContextResponse(List<ModelContextProtocolContextRequest> contextRequests)
            implements ModelServerResponse {}

    @Builder
    record ModelServerCodeResponse<T>(ModelServerCodingAiClient.CodeResult<T> codeResult)
            implements ModelServerResponse {}

}