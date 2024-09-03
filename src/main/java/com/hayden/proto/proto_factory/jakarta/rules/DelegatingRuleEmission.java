package com.hayden.proto.proto_factory.jakarta.rules;

import com.hayden.proto.proto.Prototype;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationRule;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationTarget;
import com.hayden.proto.ty.CompositePrototypedBehavior;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.constraints.*;

@Slf4j
public class DelegatingRuleEmission implements RuleEmission {

    @Override
    public Map<JakartaValidationTarget, JakartaValidationRule> emitRules(Class<? extends CompositePrototypedBehavior<Prototype>> from) {
        var clazz = from.getClass();

        for (var a : clazz.getAnnotations()) {
            switch(a) {
                case Nullable n -> {

                }
                case Size s -> {

                }
                default -> {}
            }
        }

//        Arrays.stream(from.getDeclaredFields())
//                .map(f -> {
//
//                });

        return new HashMap<>();
    }
}
