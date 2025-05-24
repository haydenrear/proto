package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.hayden.proto.prototype.datasource.data.inputs.request.RetryProto;
import com.hayden.proto.prototyped.sources.client.RequestSourceDesc;
import com.hayden.proto.prototyped.sources.data.inputs.Url;
import com.hayden.proto.prototyped.sources.data.inputs.request.Body;
import com.hayden.proto.prototyped.sources.data.inputs.request.Path;
import com.hayden.proto.prototyped.sources.requestresponse.Headers;
import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.datasource.data.wire.StaticWireProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.StaticApiRequestContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.WireTypeRequestContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.data.ModelServerRecordProto;
import com.hayden.proto.prototyped.sources.retry.Retry;
import com.hayden.utilitymodule.ctx.PrototypeScope;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Slf4j
@Component
@PrototypeScope
@RequestSourceDesc(proto = ModelServerChatRequest.ModelServerApiRequestContractProto.class)
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelServerChatRequest implements WithRetryParams {

    @Override
    public void addExceptionMessage(String exceptionMessage) {
        this.content = this.content.withException(exceptionMessage);
    }

    public record ChatMessage(String role, String content) {
        public ChatMessage(String content) {
            this("system", content);
        }
    }

    @Body(proto = ModelServerRecordProto.class)
    public record ModelServerBody(List<ChatMessage> messages, ModelServerRequestType requestType,
                                  @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                  @JsonProperty(value = "Retrying request. Saw this message last time - please try again:") String exceptionMessage) {

        public ModelServerBody(List<ChatMessage> messages, ModelServerRequestType requestType) {
            this(messages, requestType, null);
        }

        public ModelServerBody(String prompt, ModelServerRequestType requestType) {
            this(Lists.newArrayList(new ChatMessage(prompt)), requestType, null);
        }

        public ModelServerBody(String prompt) {
            this(prompt, null);
        }

        public ModelServerBody withRequestType(ModelServerRequestType requestType) {
            return new ModelServerBody(this.messages, requestType, exceptionMessage);
        }

        public ModelServerBody withException(String exceptionMessage) {
            return new ModelServerBody(this.messages, requestType, exceptionMessage);
        }
    }

    public enum ModelServerRequestType {
        TOOLSET, CODEGEN, INITIAL_CODE, VALIDATION, RERANK
    }

    ModelServerBody content;

    @Headers(proto = ModelServerAiRequestHeaders.class)
    HttpHeaders headers;
    @Url
    String url;
    @Path
    String path;
    @Retry(proto = RetryProto.class)
    @Getter
    RetryParameters retryParameters;

    int maxLength;

    public ModelServerChatRequest(ModelServerBody content) {
        this.content = content;
    }

    public record ModelServerAiRequestHeaders()
            implements AiRequestConstructProto.AiRestContract.AiRequestHeader {}

    public record ModelServerApiRequestContractProto(Plural<AiRequestConstructProto> requestConstructProto)
            implements StaticApiRequestContractProto {

        @Override
        public Any<AiRequestConstructProto> requestContracts() {
            return () -> requestConstructProto.pluralize().toArray(AiRequestConstructProto[]::new);
        }

        @Override
        public WireTypeRequestContractProto wire() {
            return () -> StaticWireProto.EMPTY;
        }
    }

}
