package com.hayden.proto.prototype.factory;

import com.hayden.proto.prototype.Prototype;

import java.nio.file.Path;
import java.util.Optional;

// TODO: prototype embedding can be generated from Jakarta annotations, for instance, for metadata and saving into graphQL API.
//      Then the clients can be extended as a proxy with that metadata so they don't know about it. For example an aspect
//      added that extends at runtime with the contractual meta-embedding based on metadata sources, saving to database and making
//      available through the API. Introspection ~ Bindings and Actions
public interface ProtoFactory<T extends Prototype, P extends ProtoSource<T>> {

    Class<P> propertySourceClazz();

    T createProto();

    default Optional<ProtoSource<T>> from(P p) {
        var created = this.createProto();
        return Optional.ofNullable(created)
                .map(c -> () -> c);
    }

}
