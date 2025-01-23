package com.hayden.proto.prototype.factory.persistdescript;

import com.hayden.proto.prototyped.sources.client.DataClient;

/**
 * Needed:
 *  communicate metadata about the program past just simple REST calls.
 *  Think OpenAPI for the entire stack - to abstract over the program and then
 *  the entire architecture in an ergonomic and programmatic way, so you can write
 *  programs about programs, that use the programs, validate the programs, etc.
 *
 *  So then this will be the main primitive returned in the GraphQL as frontend-gen
 *  embedding, and also building out graphs about the programs for validation embedding.
 */
public sealed interface ProgramDescriptorTarget {

    Class<?> typed();

    record DataClientTarget(DataClient dataClient, Class<?> typed) implements ProgramDescriptorTarget {}

    record DataFieldTarget(Class<?> typed) implements ProgramDescriptorTarget { }

    record DataTypeTarget(Class<?> typed) implements ProgramDescriptorTarget { }


}
