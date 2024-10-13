package com.hayden.proto.prototype.factory.persistdescript.rules;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.factory.persistdescript.ValidationRule;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import com.hayden.proto.prototyped.CompositePrototypedBehavior;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.constraints.*;

@Slf4j
public class DelegatingRuleEmission implements RuleEmission {

    @Override
    public Map<ProgramDescriptorTarget, ValidationRule> emitRules(Class<?> from) {
        for (var a : from.getAnnotations()) {
            switch(a) {
                case Nullable n -> {
                }
                case Size s -> {
                }
                default -> {}
            }
        }

        return new HashMap<>();
    }
}
