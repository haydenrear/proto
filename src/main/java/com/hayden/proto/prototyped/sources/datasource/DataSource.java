package com.hayden.proto.prototyped.sources.datasource;

import com.hayden.proto.prototype.datasource.DataSourceContractProto;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.client.RestDataSourceClient;
import com.hayden.proto.prototyped.sources.data.db.DataRepo;
import com.hayden.proto.prototyped.sources.server.ComTroller;

public @interface DataSource {

    DataClient[] dataClient() default {};

    RestDataSourceClient[] restDataSourceClient() default {};

    ComTroller[] comtrollers() default {};

    DataRepo[] repos() default {};

    Class<? extends DataSourceContractProto>[] proto() default {};

}
