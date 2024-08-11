package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.data.patterns.RegexPatternContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface PathContractProto extends RequestConstructContractProto
        permits
            PathContractProto.PermitsRegexPatternProto {

    record PermitsRegexPatternProto(RegexPatternContractProto permitting)
            implements Permitting<RegexPatternContractProto>, PathContractProto {}

}