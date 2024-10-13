package com.hayden.proto.prototyped.metadata.repo;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototyped.metadata.model.PrototypeModel;

import java.util.List;
import java.util.Optional;

public interface MetadataRepo {

    Optional<PrototypeModel> save(PrototypeModel prototype);

    Optional<PrototypeModel> saveAll(List<PrototypeModel> prototype);


}
