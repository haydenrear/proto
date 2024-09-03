package com.hayden.proto.datasources.ai.huggingface.client;

import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.proto.datasource_proto.data.response.DataRecordResponseConstructContractProto;
import com.hayden.proto.datasource_proto.data.response.ResponseConstructContractProto;
import com.hayden.proto.datasource_proto.data.response.WireTypeResponseConstructContractProto;
import com.hayden.proto.datasource_proto.exec.ExecContractProto;
import com.hayden.proto.datasource_proto.inputs.request.WireTypeRequestContractProto;
import com.hayden.proto.datasource_proto.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.datasources.ai.huggingface.data.ModelServerRecordProto;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

@Builder
public record ModelServerContractProto(
        ModelServerRequestContract requestConstructs,
        ModelServerResponseContract response
) implements DataSourceClientContractProto {

    public ModelServerContractProto(
            int contextLength,
            String modelEndpoint
    ) {
        this(
                requestContracts(contextLength, modelEndpoint, new ModelServerRecordProto()),
                // should be retrieved from responseConstructContractProtos above
                responseContracts(new ResponseConstructContractProto[]{}, null)
        );
    }

    public ModelServerContractProto(
            int contextLength,
            String modelLocation,
            ModelServerRecordProto response,
            ResponseConstructContractProto[] responseConstructContractProtos
    ) {
        this(
                requestContracts(contextLength, modelLocation, response),
                // should be retrieved from responseConstructContractProtos above
                responseContracts(responseConstructContractProtos, null)
        );
    }

    public static ModelServerResponseContract responseContracts(ResponseConstructContractProto[] responseConstructContractProtos,
                                                                DataRecordResponseConstructContractProto constructContractProto) {
        return new ModelServerResponseContract() {
            @Override
            public Any<ResponseConstructContractProto> responseContracts() {
                return () -> responseConstructContractProtos;
            }

            @Override
            public WireTypeResponseConstructContractProto wire() {
                // TODO:
//                    return () -> new StreamingWireProto.PermittingResponseStream<>();
                throw new RuntimeException("");
            }
        };
    }

    private static @NotNull ModelServerRequestContract requestContracts(int contextLength, String modelEndpoint, ModelServerRecordProto permitting) {
        return new ModelServerRequestContract() {
            @Override
            public Any<AiRequestConstructProto> requestContracts() {
                return () -> new AiRequestConstructProto[]{
                        new AiRequestConstructProto.AiContextLengthProtoValue.PermitsNumberContractProto(contextLength),
                        new AiRequestConstructProto.AiModelLocationProtoValue.PermitsStringContractProto(modelEndpoint),
                        new AiRequestConstructProto.AiRestContract.AiRestBody(permitting)
                };
            }

            @Override
            public WireTypeRequestContractProto wire() {
                return null;
            }
        };
    }

    public String url() {
        return this.requestConstructs()
                .requestContracts()
                .retrieve(AiRequestConstructProto.AiModelLocationProtoValue.PermitsStringContractProto.class)
                .orElseThrow()
                .value()
                .value();
    }

    @Override
    public Any<AiRequestConstructProto> requestContracts() {
        return this.requestConstructs.requestContracts();
    }

    @Override
    public Any<ResponseConstructContractProto> responseContracts() {
        return response.responseContracts();
    }

    @Override
    public ExecContractProto exec() {
        return () -> () -> new ExecContractProto.ExecutionDescriptor[0];
    }

}
