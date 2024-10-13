package com.hayden.proto.prototyped.metadata.repo;

import com.hayden.proto.prototyped.metadata.model.PrototypeModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class NoOpMetadataRepo implements MetadataRepo {

    private AtomicInteger saved = new AtomicInteger(0);

    @Override
    public Optional<PrototypeModel> save(PrototypeModel prototype) {
        saved.incrementAndGet();
        return Optional.of(prototype);
    }

    @Override
    public Optional<PrototypeModel> saveAll(List<PrototypeModel> prototype) {
        saved.set(saved.get() + prototype.size());
        return Optional.empty();
    }
}
