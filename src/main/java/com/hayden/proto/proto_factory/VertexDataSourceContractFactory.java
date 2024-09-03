package com.hayden.proto.proto_factory;

import com.hayden.proto.datasources.ai.google.client.VertexResponseStreamAiClient;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class VertexDataSourceContractFactory
        implements ProtoFactory<VertexResponseStreamAiClient.VertexDataSourceClientContractProto, VertexPropertySource> {

    @SneakyThrows
    @Override
    public Path loadFrom() {
        return new PathMatchingResourcePatternResolver()
                .getResource("classpath:vertex.yml")
                .getFile()
                .toPath();
    }

    @Override
    public Class<VertexPropertySource> propertySourceClazz() {
        return VertexPropertySource.class;
    }

    @Override
    public VertexResponseStreamAiClient.VertexDataSourceClientContractProto createProto(VertexPropertySource protoSource) {
        return protoSource.build();
    }

}
