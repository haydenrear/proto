package com.hayden.proto.prototype.factory.persistdescript;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.factory.AnnotationDrivenProtoSourceFactory;
import com.hayden.proto.prototype.factory.ProtoSource;
import com.hayden.proto.prototype.factory.ProtoSourceFactory;
import com.hayden.utilitymodule.MapFunctions;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ValidationProtoSourceFactory
        extends AnnotationDrivenProtoSourceFactory<Prototype> {

    Map<ProgramDescriptorTarget, ValidationRule> emitRules(Class<?> from);

    default Map<ProgramDescriptorTarget, ProtoSource<Prototype>> from(Class<?> aClass) {
        return MapFunctions.CollectMap(
                Optional.ofNullable(emitRules(aClass)).stream()
                        .flatMap(e -> e.entrySet().stream()
                                .map(rulesEntry -> Map.entry(rulesEntry.getKey(), rulesEntry.getValue().prototype())))
        );
    }

}
