package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.ManyOf;
import com.hayden.proto.datasource_proto.cardinality.Plural;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.datasource_proto.inputs.request.RequestConstructContractProto;
import com.hayden.proto.datasource_proto.inputs.request.WireTypeRequestContractProto;
import com.hayden.proto.proto.CompositePrototype;
import com.hayden.proto.proto.Prototype;
import com.hayden.proto.ty.CompositePrototyped;

public interface ResponseContractProto
        extends CompositePrototype<ResponseConstructContractProto> {

    Plural<? extends ResponseConstructContractProto> responseContracts();


    default Plural<? extends ResponseConstructContractProto> prototypes() {
        return responseContracts();
    }


    WireTypeResponseConstructContractProto wire();

    // validate the response
//    <T> boolean isValidResponse(T in);

    // validate the response
//    <T> boolean isValidRequest(T in);

    // TODO:
//    interface Parser {}

    // compile a parser
//    <T> Parser createResponseParser(T in);

}
