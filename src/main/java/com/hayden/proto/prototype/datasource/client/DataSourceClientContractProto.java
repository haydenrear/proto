package com.hayden.proto.prototype.datasource.client;

import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestContractProto;
import com.hayden.proto.prototype.datasource.data.response.ResponseConstructContractProto;
import com.hayden.proto.prototype.datasource.data.response.ResponseContractProto;
import com.hayden.proto.prototype.datasource.exec.ExecContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestConstructContractProto;
import com.hayden.proto.prototype.Prototype;

public interface DataSourceClientContractProto extends Prototype {

    RequestContractProto requestContracts();

    ResponseContractProto responseContracts();

    ExecContractProto exec();

}
