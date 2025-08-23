package com.hayden.proto.prototype.factory;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import com.hayden.proto.prototype.factory.persistdescript.ValidationProtoSourceFactory;
import com.hayden.proto.prototype.factory.persistdescript.ValidationProtoSourceFactoryImpl;
import com.hayden.proto.prototype.factory.persistdescript.rules.type.TypeDelegateRuleEmission;
import com.hayden.proto.prototyped.metadata.model.PrototypeModel;
import com.hayden.proto.prototyped.metadata.repo.MetadataRepo;
import com.hayden.proto.prototyped.metadata.repo.NoOpMetadataRepo;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.QueryFunction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/**
 * For example, if a component is annotated with @DataFieldContract, then it has @Autowired on DataFieldContractProto
 * and then there is an autoconfigured ProtoFactory (generic) that reflectively auto-detects it's contracts based on
 * it's other annotations, for example validation annotations.
 *
 * Then maybe even the field is removed and is not included in the model itself, but instead modeled independently.
 */
@Configuration
@NoArgsConstructor
@Import(TypeDelegateRuleEmission.class)
@ConditionalOnProperty(value = "proto.enabled", havingValue = "true")
public class ProtoFactoryConfig {

    @Bean
    @ConditionalOnProperty(value = "proto.enabled", havingValue = "true")
    public ValidationProtoSourceFactory validationProtoSourceFactory(TypeDelegateRuleEmission typeDelegateRuleEmission) {
        return new ValidationProtoSourceFactoryImpl(typeDelegateRuleEmission);
    }

    @Bean
    @ConditionalOnProperty(value = "proto.enabled", havingValue = "true")
    public BeanFactoryPostProcessor protoSourceFactory(
            @Autowired(required = false) Collection<ProtoSourceFactory> protoSourceFactory
    ) {
        var r = new Reflections(new ConfigurationBuilder().forPackages("com.hayden", "")
                .setScanners(Scanners.SubTypes.filterResultsBy(s -> true)));
        var objects = r.getSubTypesOf(Object.class);

        return beanFactory -> {
            var prototypes = parsePrototypes(protoSourceFactory, objects).stream()
                    .flatMap(proto -> {
                        Prototype build = proto.getValue().build();
                        return pluralizeRecursive(build, proto.getKey());
                    })
                    .toList();

            prototypes.forEach(p -> beanFactory.registerSingleton(p.getValue().getClass().getSimpleName(), p.getValue()));
            prototypes.stream().map(Map.Entry::getValue)
                    .forEach(beanFactory::autowireBean);
        };
    }


    @Bean
    @ConditionalOnProperty(value = "proto.enabled", havingValue = "true")
    @ConditionalOnMissingBean(MetadataRepo.class)
    public MetadataRepo metadataRepo() {
        return new NoOpMetadataRepo();
    }

    @Bean
    @ConditionalOnProperty(value = "proto.enabled", havingValue = "true")
    public CommandLineRunner cmd(List<Prototype> prototypes,
                          ApplicationContext ctx,
                          MetadataRepo metadataRepo) {
        prototypes.forEach(prototype -> prototype.wirePrototype(ctx));
        return args -> prototypes.stream().map(p -> new PrototypeModel(UUID.randomUUID().toString(), p)).forEach(metadataRepo::save);
    }

    @Bean
    @ConditionalOnProperty(value = "proto.enabled", havingValue = "true")
    public BeanFactoryPostProcessor beanFactoryPostProcessor(Collection<? extends ProtoFactory> protoFactory) {
        return beanFactory -> protoFactory.stream()
                .peek(beanFactory::autowireBean)
                .forEach(p -> {
                    Prototype proto = p.createProto();
                    beanFactory.registerSingleton(StringUtils.uncapitalize(proto.getClass().getName()), proto);
                });
    }

    public Stream<Map.Entry<ProgramDescriptorTarget, Prototype>> pluralizeRecursive(Prototype prototype, ProgramDescriptorTarget target) {
        if (prototype instanceof Plural<?> p) {
            return p.pluralize()
                    .flatMap(pl ->  pl instanceof Plural<?> plp
                                    ? this.pluralizeRecursive(plp, target)
                                    : pl instanceof Prototype proto
                                        ? Stream.of(Map.entry(target, proto))
                                        : Stream.empty()
                    );
        }

        return Stream.of(Map.entry(target, prototype));
    }



    private static @NotNull List<Map.Entry<ProgramDescriptorTarget, ProtoSource<Prototype>>> parsePrototypes(Collection<ProtoSourceFactory> protoSourceFactory, Set<Class<?>> objects) {
        List<Map.Entry<ProgramDescriptorTarget, ProtoSource<Prototype>>> values = Optional.ofNullable(protoSourceFactory).stream()
                .flatMap(Collection::stream)
                .filter(p -> !(p instanceof ProtoFactory<?,?>))
                .flatMap(p -> objects.stream().flatMap(o -> p.from(o).entrySet().stream()))
                .toList();
        return values;
    }
}
