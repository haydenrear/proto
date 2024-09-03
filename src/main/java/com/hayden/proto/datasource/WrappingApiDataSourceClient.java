package com.hayden.proto.datasource;

import com.hayden.proto.datasource.inputs.request.api.ApiRequest;

public interface WrappingApiDataSourceClient<RES extends DataSourceClient.DataRecordResponseRecord, REQ extends ApiRequest>
        extends DataSourceClient<REQ, RES> {

}
