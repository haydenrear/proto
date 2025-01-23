package com.hayden.proto.prototype.datasource;

import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.client.DataSourceClientContractProto;
import com.hayden.proto.prototype.Prototype;
import com.hayden.proto.prototype.datasource.data.db.RepoContractProto;
import com.hayden.proto.prototype.datasource.data.db.consistency.ConsistencyContractProto;

/**
 * Situation:
 *  someone asks for a page or wants to interface with a particular embedding source. We are multi-
 *  UI, so the question, how do we generate a UI, how do we create the interface? And the answer is
 *  pick any interface (out of a db for example) and add anything that fits with the associated
 *  protocols. They want voice UI. We have many voice UI, for each protocol there exists an adapter,
 *  ask for this set of protocols, provide the UI. Or alternatively a embedding source has so many
 *  compatible UI that implement the associated protocols.
 */
public interface DataSourceContractProto extends Prototype {

    Plural<? extends RepoContractProto> repoContractProto();

    ConsistencyContractProto consistencyContractProto();

}