package com.hayden.proto.datasources.ai.google.data;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.Many;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.datasource_proto.data.value.AdtContractProto;
import com.hayden.proto.datasource_proto.data.value.ByteChunkContractProto;
import com.hayden.proto.datasource_proto.data.value.DataValueContractProto;

import java.util.Map;

public class VertexDataRecordProto implements DataRecordContractProto {

    @Override
    public Many<DataValueContractProto> values() {
        return () -> new DataValueContractProto.PermittingValueContractProto(new ValueContractProto.PermittingAdt(new AdtContractProto() {}));
    }
}
