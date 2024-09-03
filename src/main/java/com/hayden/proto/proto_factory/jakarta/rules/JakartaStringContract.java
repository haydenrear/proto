package com.hayden.proto.proto_factory.jakarta.rules;

import com.hayden.proto.proto_factory.jakarta.JakartaValidationRule;
import com.hayden.proto.proto_factory.jakarta.JakartaValidationTarget;

public interface JakartaStringContract extends JakartaValidationRule {

    record JakartaNullabilityString(JakartaValidationTarget selfTarget, JakartaValidationTarget parentTarget,
                                    Nullability nullability)
            implements JakartaStringContract {
        public enum Nullability {
            NULLABLE, NON_NULLABLE
        }
    }

    record JakartaStringLength(JakartaValidationTarget selfTarget, JakartaValidationTarget parentTarget,
                               Nullability nullability)
            implements JakartaStringContract {
        public enum Nullability {
            NULLABLE, NON_NULLABLE
        }
    }


}
