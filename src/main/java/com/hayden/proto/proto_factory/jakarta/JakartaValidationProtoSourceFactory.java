package com.hayden.proto.proto_factory.jakarta;

import com.hayden.proto.proto.Prototype;
import com.hayden.proto.proto_factory.AnnotationDrivenProtoSourceFactory;
import com.hayden.proto.proto_factory.ProtoSource;
import com.hayden.proto.ty.CompositePrototypedBehavior;

import java.util.List;
import java.util.Map;


public interface JakartaValidationProtoSourceFactory
        extends AnnotationDrivenProtoSourceFactory<CompositePrototypedBehavior<Prototype>, Prototype> {


    interface ContractAdapter {
        JakartaValidationContract fromRule(Map.Entry<JakartaValidationTarget, JakartaValidationRule> rule);
    }

    interface ProtoSourceAdapter {
        ProtoSource<Prototype> from(List<JakartaValidationContract> contracts);
    }

    ContractAdapter delegatingAdapter();

    Map<JakartaValidationTarget, JakartaValidationRule> emitRules(Class<? extends CompositePrototypedBehavior<Prototype>> from);

    ProtoSourceAdapter adapter();

    default ProtoSource<Prototype> from(Class<? extends CompositePrototypedBehavior<Prototype>> aClass) {
        return adapter().from(
                emitRules(aClass).entrySet()
                        .stream()
                        .map(delegatingAdapter()::fromRule)
                        .toList()
        );
    }

}
