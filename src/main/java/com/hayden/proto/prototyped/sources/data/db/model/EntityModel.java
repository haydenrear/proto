package com.hayden.proto.prototyped.sources.data.db.model;

import com.hayden.proto.prototype.datasource.data.db.model.EntityContractProto;

public @interface EntityModel {

    Class<? extends EntityContractProto>[] proto() default {};

}
