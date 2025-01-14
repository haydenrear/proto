package com.hayden.proto.prototyped.sources.retry;

import com.hayden.proto.prototype.datasource.data.inputs.HeadersContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RetryProto;

public @interface Retry {
    Class<? extends RetryProto>[] proto() default {};
}
