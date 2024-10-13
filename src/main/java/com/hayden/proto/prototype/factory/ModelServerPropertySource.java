package com.hayden.proto.prototype.factory;

import com.hayden.proto.prototyped.sources.data.inputs.Url;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.patterns.UrlPatternContractProto;
import com.hayden.proto.prototype.value.StringContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerContractProto;
import com.hayden.proto.prototype.Prototype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "model-server")
@NoArgsConstructor
@AllArgsConstructor
@Component
@Data
public class ModelServerPropertySource  {

    int contextLength;

    @Url(proto = ModelServerAiUrl.class)
    String modelLocation;

    public record ModelServerAiUrl()
            implements AiRequestConstructProto.AiRestContract.AiUrl<UrlPatternContractProto> {

        @Override
        public UrlPatternContractProto permitting() {
            return new PermittingUrlPatternContractProto() {
                @Override
                public Prototype permitting() {
                    return new StringContractProto() {
                        @Override
                        public Plural<? extends StringContractItemProto> prototypes() {
                            return StringContractProto.super.prototypes();
                        }
                    };
                }
            };
        }
    }

}
