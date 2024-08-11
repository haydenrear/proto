package com.hayden.proto.datasource.data.response.rest;

import com.hayden.proto.datasource.DataSourceClient;
import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.data.wiretype.StreamingWireProto;
import reactor.core.publisher.Flux;

public sealed interface StreamingRestResponse<T, W extends StreamingWireProto<DataRecordContractProto>>
        extends DataSourceClient.DataRecordResponseRecord<W>
    permits
        StreamingRestResponse.FluxStreamingResponse,
        StreamingRestResponse.IterableStreamingResponse {


    record FluxStreamingResponse<T>(Flux<T> t) implements StreamingRestResponse<T, StreamingWireProto.PermittingFlux<DataRecordContractProto>> {
        @Override
        public DataSourceClient.DataRecordResponseContract<StreamingWireProto.PermittingFlux<DataRecordContractProto>> proto() {
            return null;
        }
    }

    record IterableStreamingResponse<T>(Iterable<T> t) implements StreamingRestResponse<T, StreamingWireProto.PermittingIterable<DataRecordContractProto>> {
        @Override
        public DataSourceClient.DataRecordResponseContract<StreamingWireProto.PermittingIterable<DataRecordContractProto>> proto() {
            return null;
        }
    }

}
