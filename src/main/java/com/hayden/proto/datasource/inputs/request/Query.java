package com.hayden.proto.datasource.inputs.request;

import com.hayden.proto.datasource_proto.inputs.request.QueryContractProto;
import com.hayden.proto.ty.Prototyped;

public interface Query<T> extends Prototyped<QueryContractProto, T> {
}