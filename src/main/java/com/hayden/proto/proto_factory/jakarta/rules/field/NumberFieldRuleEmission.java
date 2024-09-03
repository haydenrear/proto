package com.hayden.proto.proto_factory.jakarta.rules.field;

import com.hayden.proto.proto_factory.jakarta.JakartaValidationRule;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationTarget;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Component
public class NumberFieldRuleEmission implements FieldRuleEmission {


    @Override
    public Optional<JakartaValidationRule> emitRules(FieldRuleEmissionArg arg) {
        return Optional.empty();
    }
}
