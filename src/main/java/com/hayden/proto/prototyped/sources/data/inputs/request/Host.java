package com.hayden.proto.prototyped.sources.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.inputs.request.HostContractProto;
import com.hayden.proto.prototyped.Prototyped;

public @interface Host{

    Class<? extends HostContractProto>[] proto() default {};

}

