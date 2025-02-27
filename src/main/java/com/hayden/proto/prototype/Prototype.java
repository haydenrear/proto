package com.hayden.proto.prototype;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import com.hayden.utilitymodule.result.error.SingleError;
import org.springframework.context.ApplicationContext;

public interface Prototype {

    interface PrototypeError extends SingleError {}

    @JsonIgnore
    default void wirePrototype(ApplicationContext ctx) {}

    @JsonIgnore
    default ProgramDescriptorTarget getProgramDescriptorTarget() {
        throw new RuntimeException("Not implemented");
    }

    @JsonIgnore
    default void setProgramDescriptorTarget(ProgramDescriptorTarget target) {}

}
