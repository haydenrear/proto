package com.hayden.proto.proto;

import com.hayden.proto.ty.Prototyped;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.error.ErrorCollect;

public interface DelegatingPrototype<P extends Prototype> extends Prototype {

    P delegator();

}
