package com.hayden.proto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProtoApplicationTests {

    @SpringBootApplication
    public static class DoTest {
        public static void main(String[] args) {
            SpringApplication.run(DoTest.class, args);
        }
    }

    @Test
    void contextLoads() {
    }

}
