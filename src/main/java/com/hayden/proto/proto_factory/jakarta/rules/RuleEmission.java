package com.hayden.proto.proto_factory.jakarta.rules;

import com.hayden.proto.proto.Prototype;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationRule;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationTarget;
import com.hayden.proto.ty.CompositePrototypedBehavior;

import java.util.Map;

public interface RuleEmission {

    Map<JakartaValidationTarget, JakartaValidationRule> emitRules(Class<? extends CompositePrototypedBehavior<Prototype>> from);

}
