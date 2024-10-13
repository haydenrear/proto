package com.hayden.proto.prototype.datasource.data.response;

import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.data.wire.WireContractProto;
import com.hayden.proto.prototype.CompositePrototype;

public interface ResponseContractProto
        extends CompositePrototype<ResponseConstructContractProto> {

    Plural<? extends ResponseConstructContractProto> responseContracts();


    default Plural<? extends ResponseConstructContractProto> prototypes() {
        return responseContracts();
    }


    default WireTypeResponseConstructContractProto wire() {
        return new WireTypeResponseConstructContractProto() {
            @Override
            public WireContractProto contract() {
                return null;
            }
        };
    }

    // validate the response
//    <T> boolean isValidResponse(T in);

    // validate the response
//    <T> boolean isValidRequest(T in);

    // TODO:
//    interface Parser {}

    // compile a parser
//    <T> Parser createResponseParser(T in);

}
