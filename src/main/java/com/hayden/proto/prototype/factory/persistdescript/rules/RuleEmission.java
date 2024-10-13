package com.hayden.proto.prototype.factory.persistdescript.rules;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.factory.persistdescript.ValidationRule;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import com.hayden.proto.prototyped.CompositePrototypedBehavior;

import java.util.Map;

public interface RuleEmission {

    Map<ProgramDescriptorTarget, ValidationRule> emitRules(Class<?> from);

}
