package com.hayden.proto.prototype.factory.persistdescript.rules.field;

import com.hayden.proto.prototype.factory.persistdescript.ValidationRule;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NumberFieldRuleEmission implements FieldRuleEmission {


    @Override
    public Optional<ValidationRule> emitRules(FieldRuleEmissionArg arg) {
        return Optional.empty();
    }
}
