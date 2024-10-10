package com.hayden.proto.proto_factory;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

/**
 * For example, if a component is annotated with @DataFieldContract, then it has @Autowired on DataFieldContractProto
 * and then there is an autoconfigured ProtoFactory (generic) that reflectively auto-detects it's contracts based on
 * it's other annotations, for example validation annotations.
 *
 * Then maybe even the field is removed and is not included in the model itself, but instead modeled independently.
 */
@Configuration
@NoArgsConstructor
public class ProtoFactoryConfig {

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor(Collection<? extends ProtoFactory> protoFactory) {
        return beanFactory -> protoFactory.stream()
                .peek(beanFactory::autowireBean)
                .forEach(p -> {
                    Yaml y = new Yaml();
                    try (var fr = new FileReader(p.loadFrom().toFile())) {
                        var props = y.loadAs(fr, p.propertySourceClazz());
                        var proto = p.createProto((ProtoSource) props);
                        beanFactory.registerSingleton(proto.getClass().getSimpleName(), proto);
                    } catch (IOException e) {
                    }
                });
    }

}
