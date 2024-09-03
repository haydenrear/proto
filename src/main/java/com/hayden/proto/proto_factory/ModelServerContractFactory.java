package com.hayden.proto.proto_factory;

import com.hayden.proto.datasources.ai.huggingface.client.ModelServerContractProto;
import com.hayden.proto.datasources.ai.huggingface.client.ModelServerEmbeddingAiClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class ModelServerContractFactory
        implements ProtoFactory<ModelServerContractProto, ModelServerPropertySource> {

    @SneakyThrows
    @Override
    public Path loadFrom() {
        return new PathMatchingResourcePatternResolver()
                .getResource("classpath:model-server.yml")
                .getFile()
                .toPath();
    }

    @Override
    public Class<ModelServerPropertySource> propertySourceClazz() {
        return ModelServerPropertySource.class;
    }

    @Override
    public ModelServerContractProto createProto(ModelServerPropertySource protoSource) {
        return protoSource.build();
    }

}
