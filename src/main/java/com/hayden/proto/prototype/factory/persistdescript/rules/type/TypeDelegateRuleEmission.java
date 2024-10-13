package com.hayden.proto.prototype.factory.persistdescript.rules.type;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.factory.ProtoSource;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import com.hayden.proto.prototype.factory.persistdescript.ValidationRule;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TypeDelegateRuleEmission implements TypeRuleEmission, ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public Optional<ValidationRule> emitRules(TypeRuleEmissionArg from) {
        switch(from.self()) {
            case ProgramDescriptorTarget.DataClientTarget dataClientTarget -> {
                List<ValidationRule> validationRules = toValidationRules(from, dataClientTarget);
                return validationRules.isEmpty()
                       ? Optional.empty()
                       : Optional.of(new ValidationRule.CompositeAllValidationRule.Rules(validationRules));
            }
            case ProgramDescriptorTarget.DataFieldTarget dataFieldTarget -> {

            }
            case ProgramDescriptorTarget.DataTypeTarget dataTypeTarget -> {
            }
        }
        return Optional.empty();
    }

    private @NotNull List<ValidationRule> toValidationRules(TypeRuleEmissionArg from,
                                                            ProgramDescriptorTarget.DataClientTarget dataClientTarget) {
        return Arrays.stream(dataClientTarget.dataClient().proto())
                .map(dc -> nextValidationRules(from, p(dc, dataClientTarget)))
                .toList();
    }

    private static <P extends Prototype> ProtoSource<P> p(Class<P> p, ProgramDescriptorTarget t) {
        return () -> {
            try {
                var i = p.getConstructor().newInstance();
                i.setTarget(t);
                return i;
            } catch (InstantiationException |
                     InvocationTargetException |
                     IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static @NotNull ValidationRule nextValidationRules(TypeRuleEmissionArg from, ProtoSource created) {
        return new ValidationRule.ValidationRuleItem(from.self(), created);
    }


    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
