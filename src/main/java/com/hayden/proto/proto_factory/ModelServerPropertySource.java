package com.hayden.proto.proto_factory;

import com.hayden.proto.datasources.ai.huggingface.client.ModelServerContractProto;
import com.hayden.proto.datasources.ai.huggingface.client.ModelServerEmbeddingAiClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "model-server")
@NoArgsConstructor
@AllArgsConstructor
@Component
@Data
public class ModelServerPropertySource implements ProtoSource<ModelServerContractProto> {

    int contextLength;
    String modelLocation;

    @Override
    public ModelServerContractProto build() {
        return new ModelServerContractProto(contextLength, modelLocation);
    }
}
