package com.hayden.proto.prototype.factory.persistdescript.rules.field;

import com.hayden.proto.prototype.factory.persistdescript.ValidationRule;
import com.hayden.proto.prototype.factory.persistdescript.rules.type.TypeDelegateRuleEmission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FieldDelegateRuleEmission implements FieldRuleEmission {

    private final NumberFieldRuleEmission numberFieldRuleEmission;
    private final StringFieldRuleEmission stringFieldRuleEmission;
    private final TypeDelegateRuleEmission delegateRuleEmission;

    @Override
    public Optional<ValidationRule> emitRules(FieldRuleEmissionArg arg) {
        var field = arg.f();
        if(field.trySetAccessible()) {
            return switch (field.getType().getSimpleName()) {
                case "Integer.class",
                     "Float.class",
                     "Number.class",
                     "Long.class",
                     "Double.class",
                     "int.class",
                     "float.class",
                     "double.class",
                     "long.class" -> {
                    var e = numberFieldRuleEmission.emitRules(arg);
                    yield e;
                }
                case "String.class" -> {
                    var e = stringFieldRuleEmission.emitRules(arg);

//                    yield e.map(j -> j).or(() -> {
//                        delegateRuleEmission.emitRules()
//                    });
                    yield e;
                }
                default -> Optional.empty();
            };
        }else {
            log.error("Could not set field {} accessible to retrieve jakarta validation rules.",
                    field.getName());
            return Optional.empty();
        }
    }


}
