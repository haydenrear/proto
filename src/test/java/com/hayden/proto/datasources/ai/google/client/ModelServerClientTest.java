package com.hayden.proto.datasources.ai.google.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerEmbeddingAiClient;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerRequest;
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
    public static class ProtoApp {
        public static void main(String[] args) {
            SpringApplication.run(ProtoApp.class, args);
        }

    }

    @SneakyThrows
//    @Test
    void send() {
        System.out.println(client);
        var floated = client.send(new ModelServerRequest(new ModelServerRequest.ModelServerBody("hello!")))
                .one()
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