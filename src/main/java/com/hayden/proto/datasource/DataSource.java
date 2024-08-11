package com.hayden.proto.datasource;

import com.hayden.proto.datasource_proto.DataSourceContractProto;

@SuppressWarnings("rawtypes")
public interface DataSource extends DataSourceContractProto {

    DataSourceClient client();



}
