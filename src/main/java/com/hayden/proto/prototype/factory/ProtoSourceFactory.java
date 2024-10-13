package com.hayden.proto.prototype.factory;

import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;

import java.util.Map;
import java.util.Optional;

/**
 * Produce the ProtoSource from metadata from the class, for instance.
 */
public interface ProtoSourceFactory<PR extends Prototype, U> {

    Map<ProgramDescriptorTarget, ProtoSource<PR>> from(U u);

}
