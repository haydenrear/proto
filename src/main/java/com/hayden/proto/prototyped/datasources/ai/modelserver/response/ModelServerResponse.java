package com.hayden.proto.prototyped.datasources.ai.modelserver.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerCodingAiClient;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerResponseContract;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp.ModelContextProtocolContextRequest;
import com.hayden.proto.prototyped.sources.client.Response;
import lombok.Builder;
import org.springframework.ai.chat.messages.AssistantMessage;

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
})
public sealed interface ModelServerResponse
        permits
            ModelServerResponse.AddContextResponse,
            ModelServerResponse.ModelServerCodeResponse {

    @Builder
    record AddContextResponse(List<ModelContextProtocolContextRequest> contextRequests)
            implements ModelServerResponse {}

    @Builder
    record ModelServerCodeResponse<T>(ModelServerCodingAiClient.CodeResult<T> codeResult)
            implements ModelServerResponse {}

    record ToolCalls(List<AssistantMessage.ToolCall> codeResult){}

}