package com.hayden.proto.datasources.ai.google.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.datasources.ai.huggingface.client.ModelServerEmbeddingAiClient;
import com.hayden.proto.datasources.ai.huggingface.request.ModelServerRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ModelServerClientTest {

    @Autowired
    ModelServerEmbeddingAiClient client;


    @SpringBootApplication
    @ComponentScan("com.hayden.proto")
    public static class VertexApp {
        public static void main(String[] args) {
            SpringApplication.run(VertexApp.class, args);
        }

    }

    @SneakyThrows
//    @Test
    void send() {
        System.out.println(client);
        var floated = client.send(new ModelServerRequest(new ModelServerRequest.ModelServerBody("hello!")))
                .get().embedding().data();
        System.out.println(floated);
    }

    @Test
    void proto() {
    }

    @Test
    void retrieve() {
    }
}