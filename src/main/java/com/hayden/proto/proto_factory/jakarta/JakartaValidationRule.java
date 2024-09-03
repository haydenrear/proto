package com.hayden.proto.proto_factory.jakarta;

import java.util.List;
import java.util.Map;

public interface JakartaValidationRule {

    JakartaValidationTarget selfTarget();

    JakartaValidationTarget parentTarget();

    interface CompositeJakartaValidationRule extends JakartaValidationRule {
        List<JakartaValidationRule> rules();

        record Rules(List<JakartaValidationRule> rules,
                     JakartaValidationTarget selfTarget,
                     JakartaValidationTarget parentTarget) implements CompositeJakartaValidationRule {
        }

    }
}
