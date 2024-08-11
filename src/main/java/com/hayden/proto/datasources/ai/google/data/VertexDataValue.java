package com.hayden.proto.datasources.ai.google.data;

import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.hayden.proto.ty.Prototyped;

public record VertexDataValue(GenerateContentResponse wrapped) implements Prototyped<VertexDataRecordProto, GenerateContentResponse> {
    @Override
    public VertexDataRecordProto proto() {
        return new VertexDataRecordProto();
    }
}
