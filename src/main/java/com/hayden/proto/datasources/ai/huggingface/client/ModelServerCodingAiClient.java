package com.hayden.proto.datasources.ai.huggingface.client;

import com.hayden.proto.datasources.ai.huggingface.request.ModelServerRequest;
import com.hayden.proto.proto.Prototype;
import com.hayden.utilitymodule.result.Result;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Optional;

@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Component
public class ModelServerCodingAiClient implements
        ModelServerClient<ModelServerCodingAiClient.ModelServerCodeResponse> {

    private final ModelServerContractProto clientContractProto;

    private RestClient template;

    @PostConstruct
    public void setup() {
        template = RestClient.builder()
                .baseUrl(clientContractProto.url())
                .build();
    }

    public record CodeResult(String data) {}

    @Builder
    public record ModelServerCodeResponse(ModelServerCodingAiClient self, CodeResult codeResult)
            implements DataRecordResponseRecord {

        @Override
        public ModelServerResponseContract proto() {
            return self.clientContractProto.response();
        }

    }

    @SneakyThrows
    @Override
    public Result<ModelServerCodeResponse, DataSourceClientPrototypeError> send(ModelServerRequest request) {
        return Result.ok(
                new ModelServerCodeResponse(
                        this,
                        template.post().body(request.content())
                                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                               .headers(h -> h.putAll(request.headers()))
                                .retrieve()
                                .body(CodeResult.class)
                )
        );
    }

    @Override
    public ModelServerContractProto proto() {
        return clientContractProto;
    }

    @Override
    public <PR extends Prototype> Optional<PR> retrieve(Class<PR> prototype) {
        return Arrays.stream(this.proto().requestContracts().ofMany())
                .filter(r -> r.getClass().equals(prototype))
                .map(r -> (PR) r)
                .findAny();
    }
}
