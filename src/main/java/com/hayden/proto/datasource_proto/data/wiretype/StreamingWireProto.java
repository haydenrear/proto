package com.hayden.proto.datasource_proto.data.wiretype;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.permitting.Permitting;
import com.hayden.proto.proto.Prototype;

public sealed interface StreamingWireProto<T extends DataRecordContractProto> extends Prototype, WireContractProto
        permits
            StreamingWireProto.PermittingFlux,
            StreamingWireProto.PermittingMono,
            StreamingWireProto.PermittingStream,
            StreamingWireProto.PermittingIterable,
            StreamingWireProto.PermittingResponseStream,
            StreamingWireProto.PermittingRequestStream {

    record PermittingFlux<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto<T>, Permitting<T>{}
    record PermittingMono<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto<T>, Permitting<T>{}
    record PermittingStream<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto<T>, Permitting<T> {}
    record PermittingIterable<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto<T>, Permitting<T> {}
    record PermittingResponseStream<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto<T>, Permitting<T> {}
    record PermittingRequestStream<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto<T>, Permitting<T> {}

}