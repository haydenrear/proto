package com.hayden.proto.datasource.inputs.request;

import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.datasource_proto.inputs.request.RequestContractProto;
import com.hayden.proto.ty.CompositePrototyped;

public interface Request<T, W extends WireContractProto>
        extends CompositePrototyped<RequestContractProto<W>> {

}
