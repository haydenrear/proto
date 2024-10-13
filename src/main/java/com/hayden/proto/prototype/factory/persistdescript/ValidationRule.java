package com.hayden.proto.prototype.factory.persistdescript;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.cardinality.All;
import com.hayden.proto.prototype.factory.ProtoFactory;
import com.hayden.proto.prototype.factory.ProtoSource;
import com.hayden.proto.prototype.factory.ProtoSourceFactory;

import java.util.List;
import java.util.Optional;

public interface ValidationRule {

    ProgramDescriptorTarget target();

    ProtoSource prototype();

    record ValidationRuleItem(ProgramDescriptorTarget target, ProtoSource prototype) implements ValidationRule {}

    interface CompositeAllValidationRule extends ValidationRule {

        List<ValidationRule> rules();

        record Rules(List<ValidationRule> rules) implements CompositeAllValidationRule {
            @Override
            public ProgramDescriptorTarget target() {
                return rules.stream().findFirst().map(ValidationRule::target).orElse(null);
            }

            @Override
            public ProtoSource prototype() {
                All<Prototype> p = () -> {
                    Prototype[] array = rules.stream().map(ValidationRule::prototype)
                            .map(ps -> {
                                var pr = ps.build();
                                return pr;
                            })
                            .toArray(Prototype[]::new);
                    return array;
                };
                return () -> p;
            }
        }
    }


}
