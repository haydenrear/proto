package com.hayden.proto.prototyped.sources.data.db;

import com.hayden.proto.prototype.datasource.data.db.RepoContractProto;

public @interface DataRepo {

    Class<? extends RepoContractProto>[] proto() default {};

}