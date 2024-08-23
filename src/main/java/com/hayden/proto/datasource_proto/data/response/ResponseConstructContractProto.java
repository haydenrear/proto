package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.proto.Prototype;

public sealed interface ResponseConstructContractProto extends Prototype
        permits
            ResponseCodeContractProto,
            ResponseHeaderContractProto,
            DataRecordResponseConstructContractProto,
            WireTypeResponseConstructContractProto{



}
