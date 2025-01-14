package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototype.factory.ProtoFactoryConfigTest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes.ModelServerResponseDeser;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ModelServerCodingAiClientTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SpringBootApplication
    @ComponentScan("com.hayden.proto")
    public static class ProtoApp {
        public static void main(String[] args) {
            SpringApplication.run(ProtoFactoryConfigTest.ProtoApp.class, args);
        }
    }

    @Autowired
    private ModelServerResponseDeser modelServerCodingAiClient;
    @Autowired
    private ModelContextProtocolClient protocolClient;

    @Test
    public void testProtocolClient() {
        assertThat(protocolClient).isNotNull();
        assertThat(protocolClient.adapter).isNotNull();
    }

    @Test
    public void testAiClient() {
        ModelServerResponse.ModelServerCodeResponse test = ModelServerResponse.ModelServerCodeResponse.builder()
                .codeResult(ModelServerCodingAiClient.CodeResult.builder().data("test").build()).build();
        var f = Assertions.assertDoesNotThrow(() -> OBJECT_MAPPER.writeValueAsString(test));
        var deser = modelServerCodingAiClient.deserialize(f);
        assertThat(deser.isOk()).isTrue();
        var r = deser.r().get();
        assertThat(r).isInstanceOf(ModelServerResponse.ModelServerCodeResponse.class);
        var codeRes = (ModelServerResponse.ModelServerCodeResponse) r;
        assertThat(codeRes.codeResult()).isNotNull();
        assertThat(codeRes.codeResult().data()).isEqualTo("test");
    }

}