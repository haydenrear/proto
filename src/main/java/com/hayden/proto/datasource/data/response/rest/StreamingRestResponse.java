package com.hayden.proto.datasource.data.response.rest;

import com.hayden.proto.datasource.DataSourceClient;
import reactor.core.publisher.Flux;

public sealed interface StreamingRestResponse extends DataSourceClient.DataRecordResponseRecord
        permits
            StreamingRestResponse.FluxStreamingResponse,
            StreamingRestResponse.IterableStreamingResponse {


    record FluxStreamingResponse<T>(Flux<T> t) implements StreamingRestResponse {
        @Override
        public DataSourceClient.DataRecordResponseContract proto() {
            return null;
        }
    }

    record IterableStreamingResponse<T>(Iterable<T> t) implements StreamingRestResponse {
        @Override
        public DataSourceClient.DataRecordResponseContract proto() {
            return null;
        }
    }

}
