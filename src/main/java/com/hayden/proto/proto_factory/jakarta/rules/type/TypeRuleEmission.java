package com.hayden.proto.proto_factory.jakarta.rules.type;

import com.hayden.proto.proto_factory.jakarta.JakartaValidationRule;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationTarget;
import lombok.Builder;

import java.util.Map;
import java.util.Optional;

public interface TypeRuleEmission {

    @Builder
    record TypeRuleEmissionArg(JakartaValidationTarget self, JakartaValidationTarget parent, Class<?> f) {}

    Optional<JakartaValidationRule> emitRules(TypeRuleEmissionArg from);
}
