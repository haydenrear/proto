package com.hayden.proto.prototyped.sources.client;

import com.hayden.proto.prototype.datasource.client.DataSourceClientContractProto;

public @interface RestDataSourceClient {


    DataClient[] dataClients() default {};

}
