package com.hayden.proto.prototype.datasource.data.response;

import com.hayden.proto.prototype.Prototype;

public sealed interface ResponseConstructContractProto extends Prototype
        permits
            ResponseCodeContractProto,
            ResponseHeaderContractProto,
            DataRecordResponseConstructContractProto,
            WireTypeResponseConstructContractProto{



}
