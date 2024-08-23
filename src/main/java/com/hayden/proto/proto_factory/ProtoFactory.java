package com.hayden.proto.proto_factory;

import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.proto.Prototype;

// TODO: prototype data can be generated from Jakarta annotations, for instance, for metadata and saving into graphQL API.
//      Then the clients can be extended as a proxy with that metadata so they don't know about it. For example an aspect
//      added that extends at runtime with the contractual meta-data based on metadata sources, saving to database and making
//      available through the API. Introspection ~ Bindings and Actions
public interface ProtoFactory<T extends Prototype, P extends ProtoSource> {

    default T createProto() {
        return createProto(protoSource());
    }

    P protoSource();

    T createProto(P protoSource);

}
