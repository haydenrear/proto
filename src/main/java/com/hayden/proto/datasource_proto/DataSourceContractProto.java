package com.hayden.proto.datasource_proto;

import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.datasource_proto.client.DataSourceClientContractProto;
import com.hayden.proto.proto.Prototype;

/**
 * Situation:
 *  someone asks for a page or wants to interface with a particular data source. We are multi-
 *  UI, so the question, how do we generate a UI, how do we create the interface? And the answer is
 *  pick any interface (out of a db for example) and add anything that fits with the associated
 *  protocols. They want voice UI. We have many voice UI, for each protocol there exists an adapter,
 *  ask for this set of protocols, provide the UI. Or alternatively a data source has so many
 *  compatible UI that implement the associated protocols.
 */
public interface DataSourceContractProto extends Prototype {

    Any<DataSourceClientContractProto> clientProto();

}