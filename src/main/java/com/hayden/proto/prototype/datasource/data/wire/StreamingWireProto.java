package com.hayden.proto.prototype.datasource.data.wire;

import com.hayden.proto.prototype.datasource.DataRecordContractProto;
import com.hayden.proto.prototype.Permitting;
import com.hayden.proto.prototype.Prototype;

public sealed interface StreamingWireProto extends Prototype, WireContractProto
        permits
            StreamingWireProto.PermittingFlux,
            StreamingWireProto.PermittingMono,
            StreamingWireProto.PermittingStream,
            StreamingWireProto.PermittingIterable,
            StreamingWireProto.PermittingResponseStream,
            StreamingWireProto.PermittingRequestStream {

    record PermittingFlux<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto, Permitting<T>{}
    record PermittingMono<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto, Permitting<T>{}
    record PermittingStream<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto, Permitting<T> {}
    record PermittingIterable<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto, Permitting<T> {}
    record  PermittingResponseStream<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto, Permitting<T> {}
    record PermittingRequestStream<T extends DataRecordContractProto>(T permitting)
            implements StreamingWireProto, Permitting<T> {}

}