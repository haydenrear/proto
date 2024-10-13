package com.hayden.proto.prototype.factory.persistdescript;

import com.hayden.proto.prototype.factory.persistdescript.rules.type.TypeDelegateRuleEmission;
import com.hayden.proto.prototype.factory.persistdescript.rules.type.TypeRuleEmission;
import com.hayden.proto.prototyped.sources.client.DataClient;
import com.hayden.utilitymodule.MapFunctions;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ValidationProtoSourceFactoryImpl implements ValidationProtoSourceFactory {

    private final TypeDelegateRuleEmission typeDelegateRuleEmission;



    @Override
    public Map<ProgramDescriptorTarget, ValidationRule> emitRules(Class<?> from) {
        return MapFunctions.CollectMap(
                Arrays.stream(from.getAnnotations())
                        .flatMap(a -> switch (a) {
                            case DataClient dc -> {
                                ProgramDescriptorTarget pdt = new ProgramDescriptorTarget.DataClientTarget(dc, from);
                                yield typeDelegateRuleEmission.emitRules(new TypeRuleEmission.TypeRuleEmissionArg(pdt, from))
                                        .stream()
                                        .map(vd -> Map.entry(pdt, vd));
                            }

                            default ->
                                    Stream.empty();
                        })
        );
    }

}
