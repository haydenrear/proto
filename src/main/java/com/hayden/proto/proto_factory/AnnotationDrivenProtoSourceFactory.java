package com.hayden.proto.proto_factory;

import com.hayden.proto.proto.Prototype;
import com.hayden.proto.ty.Prototyped;

/**
 * Produce the ProtoSource from metadata from the class, for instance.
 */
public interface AnnotationDrivenProtoSourceFactory<P extends Prototyped<PR>, PR extends Prototype>
        extends ProtoSourceFactory<PR, Class<? extends P>> {

}
