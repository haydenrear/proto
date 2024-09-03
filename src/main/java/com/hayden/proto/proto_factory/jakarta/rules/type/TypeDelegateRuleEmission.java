package com.hayden.proto.proto_factory.jakarta.rules.type;

import com.hayden.proto.proto_factory.jakarta.JakartaValidationRule;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationTarget;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class TypeDelegateRuleEmission implements TypeRuleEmission {
    @Override
    public Optional<JakartaValidationRule> emitRules(TypeRuleEmissionArg from) {
        return Optional.empty();
    }
}
