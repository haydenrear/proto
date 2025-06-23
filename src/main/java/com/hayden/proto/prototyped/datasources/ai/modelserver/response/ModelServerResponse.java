package com.hayden.proto.prototyped.datasources.ai.modelserver.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerCodingAiClient;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerResponseContract;
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
        @JsonSubTypes.Type(value = ModelServerResponse.AddContextResponse.class, name = "tool"),
        @JsonSubTypes.Type(value = ModelServerResponse.ModelServerCodeResponse.class, name = "code"),
})
public sealed interface ModelServerResponse
        permits
            ModelServerResponse.AddContextResponse,
            ModelServerResponse.ModelServerCodeResponse {

    @Builder
    record AddContextResponse(List<AssistantMessage.ToolCall> toolCalls)
            implements ModelServerResponse {}

    @Builder
    record ModelServerCodeResponse<T>(ModelServerCodingAiClient.CodeResult<T> codeResult)
            implements ModelServerResponse {}

    record ToolCalls(List<AssistantMessage.ToolCall> codeResult, float version) {

        @JsonCreator
        public ToolCalls(List<AssistantMessage.ToolCall> codeResult) {
            this(codeResult, 1.0f);
        }
    }

}