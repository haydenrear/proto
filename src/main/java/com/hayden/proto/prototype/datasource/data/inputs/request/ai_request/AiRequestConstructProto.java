package com.hayden.proto.prototype.datasource.data.inputs.request.ai_request;

import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.datasource.data.patterns.UrlPatternContractProto;
import com.hayden.proto.prototype.value.NumberContractProto;
import com.hayden.proto.prototype.value.NumberValue;
import com.hayden.proto.prototype.value.StringContractProto;
import com.hayden.proto.prototype.value.StringValue;
import com.hayden.proto.prototype.datasource.data.inputs.request.BodyContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.PathContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestConstructContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestHeaderContractProto;
import com.hayden.proto.prototype.Permitting;
import com.hayden.proto.prototyped.Value;

import static com.hayden.proto.prototype.value.StringContractProto.createStringContractProto;

public sealed interface AiRequestConstructProto extends RequestConstructContractProto
    permits
        AiRequestConstructProto.AiModelNameProtoValue,
        AiRequestConstructProto.AiProjectProtoValue,
        AiRequestConstructProto.AiContextLengthProtoValue,
        AiRequestConstructProto.AiModelLocationProtoValue,
        AiRequestConstructProto.AiRestContract {

    sealed interface AiRestContract extends AiRequestConstructProto
            permits
                AiRestContract.AiRequestHeader,
                AiRestContract.AiRestBody,
                AiRestContract.AiRestPath,
                AiRestContract.AiUrl {

        non-sealed interface AiRequestHeader
                extends AiRestContract, RequestHeaderContractProto.RequestHeaderKeyValueContract { }

        record AiRestPath(PathContractProto permitting)
            implements AiRestContract, Permitting<PathContractProto> {}

        non-sealed interface AiUrl<T extends UrlPatternContractProto>
                extends AiRestContract, UrlPatternContractProto.PermittingUrlPatternContractProto<T> {}

        record AiRestBody(BodyContractProto permitting)
                implements AiRestContract, Permitting<BodyContractProto> {}

    }

    sealed interface AiModelNameProtoValue extends AiRequestConstructProto, Value<StringValue, AiModelNameProtoValue>
            permits
                AiModelNameProtoValue.PermitsStringContractProto {

        record PermitsStringContractProto(StringValue value, StringContractProto permitting)
                implements Permitting<StringContractProto>, AiModelNameProtoValue {

            public PermitsStringContractProto(String modelName) {
                this(() -> modelName, StringContractProto.EMPTY);
            }

            @Override
            public AiModelNameProtoValue proto() {
                return this;
            }
        }

    }

    sealed interface AiModelLocationProtoValue extends AiRequestConstructProto, Value<StringValue, AiModelLocationProtoValue>
            permits
                AiModelLocationProtoValue.PermitsStringContractProto {

        record PermitsStringContractProto(StringValue value, StringContractProto permitting)
                implements AiModelLocationProtoValue {

            public PermitsStringContractProto(String modelName, StringContractProto.StringContractItemProto[] protos) {
                this(() -> modelName, createStringContractProto(protos));
            }

            public PermitsStringContractProto(String modelName) {
                this(() -> modelName, StringContractProto.EMPTY);
            }

            @Override
            public AiModelLocationProtoValue proto() {
                return this;
            }
        }

    }

    sealed interface AiProjectProtoValue extends AiRequestConstructProto, Value<StringValue, AiProjectProtoValue>
            permits
                AiProjectProtoValue.PermitsStringContractProto {

        record PermitsStringContractProto(StringValue value, StringContractProto permitting)
                implements AiProjectProtoValue {

            public PermitsStringContractProto(String modelName) {
                this(() -> modelName, StringContractProto.EMPTY);
            }

            @Override
            public AiProjectProtoValue proto() {
                return this;
            }
        }

    }

    sealed interface AiContextLengthProtoValue extends AiRequestConstructProto, Value<NumberValue<Integer>, AiContextLengthProtoValue>
            permits
                AiContextLengthProtoValue.PermitsNumberContractProto {

        record PermitsNumberContractProto(NumberValue<Integer> value, NumberContractProto permitting)
                implements AiContextLengthProtoValue {
            public PermitsNumberContractProto(Integer permitting) {
                this(() -> permitting, new NumberContractProto() {});
            }

            @Override
            public AiContextLengthProtoValue proto() {
                return this;
            }
        }

    }
}
