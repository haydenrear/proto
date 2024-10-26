package com.hayden.proto.prototyped.sources.datasource;

import com.hayden.proto.prototype.datasource.DataSourceContractProto;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.proto.prototyped.sources.data.db.DataRepo;
import com.hayden.proto.prototyped.sources.monad.Collector;
import com.hayden.proto.prototyped.sources.monad.Mapper;

public @interface DataSource {

    Class<? extends DataSourceContractProto>[] proto() default {};

}
