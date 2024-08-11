package com.hayden.proto.datasource.inputs.request.api;

import com.hayden.proto.datasource.inputs.request.*;
import com.hayden.proto.datasource.inputs.request.Request;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;

public interface ApiRequest<T, W extends WireContractProto>
        extends Request<T, W> {


}
