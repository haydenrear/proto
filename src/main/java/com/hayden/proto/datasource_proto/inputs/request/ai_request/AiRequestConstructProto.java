package com.hayden.proto.datasource_proto.inputs.request.ai_request;

import com.hayden.proto.datasource_proto.data.value.NumberValueContractProto;
import com.hayden.proto.datasource_proto.data.value.StringValueContractProto;
import com.hayden.proto.datasource_proto.inputs.request.RequestConstructContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface AiRequestConstructProto extends RequestConstructContractProto
    permits
        AiRequestConstructProto.AiModelNameProto,
        AiRequestConstructProto.AiProjectContractProtoProto,
        AiRequestConstructProto.AiContextLengthContractProto,
        AiRequestConstructProto.AiModelLocationContractProto {

    sealed interface AiModelNameProto extends AiRequestConstructProto
            permits
            AiModelNameProto.PermitsStringContractProto {

        record PermitsStringContractProto(StringValueContractProto permitting)
                implements Permitting<StringValueContractProto>, AiModelNameProto {
            public PermitsStringContractProto(String modelName) {
                this(() -> modelName);
            }
        }

    }

    sealed interface AiModelLocationContractProto extends AiRequestConstructProto
            permits
            AiModelLocationContractProto.PermitsStringContractProto {

        record PermitsStringContractProto(StringValueContractProto permitting)
                implements Permitting<StringValueContractProto>, AiModelLocationContractProto {
            public PermitsStringContractProto(String modelName) {
                this(() -> modelName);
            }
        }

    }

    sealed interface AiProjectContractProtoProto extends AiRequestConstructProto
            permits
                AiProjectContractProtoProto.PermitsStringContractProto {

        record PermitsStringContractProto(StringValueContractProto permitting)
                implements Permitting<StringValueContractProto>, AiProjectContractProtoProto {
            public PermitsStringContractProto(String modelName) {
                this(() -> modelName);
            }
        }

    }

    sealed interface AiContextLengthContractProto extends AiRequestConstructProto
            permits
                AiContextLengthContractProto.PermitsNumberContractProto {

        record PermitsNumberContractProto(NumberValueContractProto<Integer> permitting)
                implements Permitting<NumberValueContractProto<Integer>>, AiContextLengthContractProto {
            public PermitsNumberContractProto(Integer permitting) {
                this(() -> permitting);
            }
        }

    }
}
