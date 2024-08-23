package com.hayden.proto.proto_factory;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * For example, if a component is annotated with @DataFieldContract, then it has @Autowired on DataFieldContractProto
 * and then there is an autoconfigured ProtoFactory (generic) that reflectively auto-detects it's contracts based on
 * it's other annotations, for example validation annotations.
 *
 * Then maybe even the field is removed and is not included in the model itself, but instead modeled independently.
 */
@Configuration
public class ProtoFactoryConfig {

    @SuppressWarnings("rawtypes")
    @Bean
    BeanFactoryPostProcessor beanFactoryPostProcessor(Collection<ProtoFactory> protoFactory) {
        return beanFactory -> protoFactory.stream()
                .peek(beanFactory::autowireBean)
                .forEach(p -> {
                    var proto = p.createProto();
                    beanFactory.registerSingleton(proto.getClass().getSimpleName(), proto);
                });
    }

}
