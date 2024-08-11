package com.hayden.proto.datasource_proto.inputs.request.ai_request;

import com.hayden.proto.datasource_proto.data.value.NumberValueContractProto;
import com.hayden.proto.datasource_proto.data.value.StringValueContractProto;
import com.hayden.proto.datasource_proto.inputs.request.RequestConstructContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface AiRequestConstructProto extends RequestConstructContractProto
    permits
        AiRequestConstructProto.AiModelNameProto,
        AiRequestConstructProto.AiProjectContractProtoProto,
        AiRequestConstructProto.ContextLength {

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


    sealed interface ContextLength extends AiRequestConstructProto
            permits
                ContextLength.PermitsNumberContractProto {

        record PermitsNumberContractProto(NumberValueContractProto<Integer> permitting)
                implements Permitting<NumberValueContractProto<Integer>>, ContextLength {
            public PermitsNumberContractProto(Integer permitting) {
                this(() -> permitting);
            }
        }

    }
}
