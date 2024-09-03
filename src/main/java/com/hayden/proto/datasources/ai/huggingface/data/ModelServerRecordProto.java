package com.hayden.proto.datasources.ai.huggingface.data;

import com.hayden.proto.datasource.inputs.request.Body;
import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.All;
import com.hayden.proto.datasource_proto.cardinality.Many;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.datasource_proto.data.value.AdtContractProto;
import com.hayden.proto.datasource_proto.data.value.DataValueContractProto;
import com.hayden.proto.datasource_proto.data.value.StringContractProto;
import com.hayden.proto.datasource_proto.inputs.request.BodyContractProto;
import com.hayden.proto.datasource_proto.inputs.search.SearchContractProto;
import com.hayden.proto.datasources.ai.huggingface.request.ModelServerRequest;
import com.hayden.proto.proto.CompositePrototype;
import com.hayden.proto.proto.DelegatingPrototype;
import com.hayden.proto.proto.Prototype;

import java.util.Map;

public class ModelServerRecordProto implements DelegatingPrototype<BodyContractProto> {

    @Override
    public BodyContractProto delegator() {
        return new BodyContractProto.PermitsSearchContractProto(new SearchContractProto.PermittingValue(
                new ValueContractProto.PermittingAdt(
                        new AdtContractProto() {
                            @Override
                            public Map<String, ValueContractProto> fields() {
                                return Map.of(
                                        "prompt", new ValueContractProto.PermittingString(StringContractProto.EMPTY)
                                );
                            }
                        })
        ));
    }

}
