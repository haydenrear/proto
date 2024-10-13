package com.hayden.proto.prototype.factory.persistdescript.rules.type;

import com.hayden.proto.prototype.factory.persistdescript.ValidationRule;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import lombok.Builder;

import java.util.Optional;

public interface TypeRuleEmission {

    @Builder
    record TypeRuleEmissionArg(ProgramDescriptorTarget self, Class<?> f) {}

    Optional<ValidationRule> emitRules(TypeRuleEmissionArg from);
}
