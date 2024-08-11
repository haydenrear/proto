package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.datasource_proto.DataRecordContractProto;
import com.hayden.proto.datasource_proto.cardinality.ManyOf;
import com.hayden.proto.datasource_proto.data.wiretype.WireContractProto;
import com.hayden.proto.proto.Prototype;

public interface ResponseContractProto<W extends WireContractProto, D extends DataRecordContractProto>
        extends Prototype {

    ManyOf<ResponseConstructContractProto> responseContracts();

    W wireContract();

}
