package com.hayden.proto.prototyped.sources.data.inputs;

import com.hayden.proto.prototype.datasource.data.patterns.UrlPatternContractProto;

public @interface Url {

    Class<? extends UrlPatternContractProto>[] proto() default {};

}
