package com.hayden.proto.datasources.ai.google.client;

import com.hayden.proto.prototyped.datasources.ai.modelserver.client.ModelServerEmbeddingAiClient;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerChatRequest;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.ModelServerEmbeddingRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ModelServerClientTest {

    @Autowired
    ModelServerEmbeddingAiClient client;


    @SpringBootApplication
    @ComponentScan("com.hayden.proto")
    @EnableAutoConfiguration(exclude = {
            DataSourceAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class
    })
    public static class ProtoApp {
        public static void main(String[] args) {
            SpringApplication.run(ProtoApp.class, args);
        }

    }

    @SneakyThrows
//    @Test
    void send() {
        System.out.println(client);
        var floated = client.send(new ModelServerEmbeddingRequest(new ModelServerEmbeddingRequest.ModelServerBody("hello!", "text-embedding-004", "")))
                .one()
                .get().embedding().embedding();
        System.out.println(floated);
    }

//    @Test
    void proto() {
    }

//    @Test
    void retrieve() {
    }
}