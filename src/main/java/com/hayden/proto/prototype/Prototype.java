package com.hayden.proto.prototype;

import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import com.hayden.utilitymodule.result.error.ErrorCollect;
import org.springframework.context.ApplicationContext;

public interface Prototype {

    interface PrototypeError extends ErrorCollect {}

    default void wirePrototype(ApplicationContext ctx) {}

    default ProgramDescriptorTarget getTarget() {
        throw new RuntimeException("Not implemented");
    }

    default void setTarget(ProgramDescriptorTarget target) {}

}
