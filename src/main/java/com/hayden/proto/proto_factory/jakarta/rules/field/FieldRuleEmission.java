package com.hayden.proto.proto_factory.jakarta.rules.field;

import com.hayden.proto.proto_factory.jakarta.JakartaValidationRule;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationTarget;
import lombok.Builder;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public interface FieldRuleEmission {

    @Builder
    record FieldRuleEmissionArg(JakartaValidationTarget self, JakartaValidationTarget parent, Field f) {}

    Optional<JakartaValidationRule> emitRules(FieldRuleEmissionArg arg);

}
