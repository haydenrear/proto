package com.hayden.proto.prototype.factory.persistdescript.rules.field;

import com.hayden.proto.prototype.factory.persistdescript.ValidationRule;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import lombok.Builder;

import java.lang.reflect.Field;
import java.util.Optional;

public interface FieldRuleEmission {

    @Builder
    record FieldRuleEmissionArg(
            ProgramDescriptorTarget self, ProgramDescriptorTarget parent, Field f) {}

    Optional<ValidationRule> emitRules(FieldRuleEmissionArg arg);

}
