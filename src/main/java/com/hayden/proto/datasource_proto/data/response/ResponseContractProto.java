package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.ManyOf;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.proto.Prototype;

public interface ResponseContractProto
        extends Prototype {

    Plural<ResponseConstructContractProto> responseContracts();

    // validate the response
//    <T> boolean isValidResponse(T in);

    // validate the response
//    <T> boolean isValidRequest(T in);

    // TODO:
//    interface Parser {}

    // compile a parser
//    <T> Parser createResponseParser(T in);

}
