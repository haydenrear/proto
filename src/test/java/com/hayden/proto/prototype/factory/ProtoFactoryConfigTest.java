package com.hayden.proto.prototype.factory;

import com.hayden.proto.prototyped.metadata.repo.NoOpMetadataRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProtoFactoryConfigTest {

    @SpringBootApplication
    @ComponentScan("com.hayden.proto")
    public static class ProtoApp {
        public static void main(String[] args) {
            SpringApplication.run(ProtoFactoryConfigTest.ProtoApp.class, args);
        }
    }

    @Autowired
    NoOpMetadataRepo noOpMetadataRepo;

    @Test
    public void doTest() {
        assertNotEquals(((AtomicInteger)ReflectionTestUtils.getField(noOpMetadataRepo, "saved")).get(), 0);
    }

}