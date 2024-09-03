package com.hayden.proto.datasource.inputs.request.rest;

import com.hayden.proto.datasource.inputs.request.*;

public interface RestRequest<T> extends Request {

    Path path();
    Host host();
    Protocol protocol();
    RestQueryParams query();
    Body<T> body();
    Limit limit();

}
