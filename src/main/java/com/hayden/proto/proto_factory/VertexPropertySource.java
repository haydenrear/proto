package com.hayden.proto.proto_factory;

import com.hayden.proto.datasources.ai.google.client.VertexResponseStreamAiClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "vertex-ai")
@NoArgsConstructor
@AllArgsConstructor
@Component
@Data
public class VertexPropertySource implements ProtoSource<VertexResponseStreamAiClient.VertexDataSourceClientContractProto> {

    int contextLength;
    String modelLocation;
    String projectId;
    String modelName;

    @Override
    public VertexResponseStreamAiClient.VertexDataSourceClientContractProto build() {
        return new VertexResponseStreamAiClient.VertexDataSourceClientContractProto(
                contextLength,
                modelLocation,
                projectId,
                modelName
        );
    }
}
