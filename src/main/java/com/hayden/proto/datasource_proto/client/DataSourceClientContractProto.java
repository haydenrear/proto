package com.hayden.proto.datasource_proto.client;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.inputs.request.RequestConstructContractProto;
import com.hayden.proto.proto.Prototype;

public interface DataSourceClientContractProto extends Prototype {

    Any<? extends RequestConstructContractProto> requestContracts();

    Any<? extends DataRecordContractProto> responseContracts();

}
