package com.hayden.proto.proto;

import com.hayden.proto.ty.Prototyped;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.ErrorCollect;

public interface Prototype {

    interface PrototypeError extends ErrorCollect {}

    default <T> Result<Boolean, PrototypeError> is(Prototyped<? extends Prototype> validate) {
        return Result.ok(true);
    }

    default <T> T retrieve(Prototyped<? extends Prototype> validate) {
        throw new RuntimeException("Did not implement");
    }

}
