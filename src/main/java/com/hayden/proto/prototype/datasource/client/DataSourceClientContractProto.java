package com.hayden.proto.prototype.datasource.client;

import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.datasource.data.response.ResponseConstructContractProto;
import com.hayden.proto.prototype.datasource.exec.ExecContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestConstructContractProto;
import com.hayden.proto.prototype.Prototype;

public interface DataSourceClientContractProto extends Prototype {

    Any<? extends RequestConstructContractProto> requestContracts();

    Any<? extends ResponseConstructContractProto> responseContracts();

    ExecContractProto exec();

}
