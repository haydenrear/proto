package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototype.factory.ProtoFactoryConfigTest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.client.serdes.ModelServerResponseDeser;
import com.hayden.proto.prototyped.datasources.ai.modelserver.response.ModelServerResponse;
import com.hayden.utilitymodule.result.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

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

    @MockBean
    private ModelServerResponseDeser modelServerCodingAiClient;
    @Autowired
    private ModelContextProtocolClient protocolClient;

    @BeforeEach
    public void setUp() {
        Mockito.when(modelServerCodingAiClient.deserialize(any()))
                .thenReturn(Result.ok(new ModelServerResponse.ModelServerCodeResponse<>(new ModelServerCodingAiClient.CodeResult<>("test"))));
    }

    @Test
    public void testProtocolClient() {
        assertThat(protocolClient).isNotNull();
        assertThat(protocolClient.adapter).isNotNull();
    }

    @Test
    public void testAiClient() {
        ModelServerResponse.ModelServerCodeResponse test = ModelServerResponse.ModelServerCodeResponse.builder()
                .codeResult(new ModelServerCodingAiClient.CodeResult("test")).build();
        var f = Assertions.assertDoesNotThrow(() -> OBJECT_MAPPER.writeValueAsString(test));
        var deser = modelServerCodingAiClient.deserialize(f);
        assertThat(deser.isOk()).isTrue();
        var r = deser.r().get();
        assertThat(r).isInstanceOf(ModelServerResponse.ModelServerCodeResponse.class);
        var codeRes = (ModelServerResponse.ModelServerCodeResponse) r;
        assertThat(codeRes.codeResult()).isNotNull();
        assertThat(codeRes.codeResult().data()).isEqualTo("test");
    }

    @Test
    public void testDeser() {

    }

}