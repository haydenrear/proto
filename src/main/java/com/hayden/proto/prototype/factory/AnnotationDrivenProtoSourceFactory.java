package com.hayden.proto.prototype.factory;

import com.hayden.proto.prototype.Prototype;

/**
 * Produce the ProtoSource from metadata from the class, for instance.
 */
public interface AnnotationDrivenProtoSourceFactory<PR extends Prototype>
        extends ProtoSourceFactory<PR, Class<?>> {

}