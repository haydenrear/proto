package com.hayden.proto.datasource.inputs.request.rest;

import com.hayden.proto.datasource.inputs.request.*;
import com.hayden.proto.datasource.inputs.request.*;
import com.hayden.proto.datasource_proto.data.wiretype.StaticWireProto;

public interface RestRequest<T> extends Request<T, StaticWireProto> {

    Path path();
    Host host();
    Protocol protocol();
    RestQueryParams query();
    Body<T> body();
    Limit limit();

}
