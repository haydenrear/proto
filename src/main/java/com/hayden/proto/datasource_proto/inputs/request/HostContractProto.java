package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.data.patterns.RegexPatternContractProto;
import com.hayden.proto.permitting.Permitting;

public sealed interface HostContractProto extends RequestConstructContractProto
        permits
            HostContractProto.PermitsRegexPatternProto {

    record PermitsRegexPatternProto(RegexPatternContractProto permitting)
            implements Permitting<RegexPatternContractProto>, HostContractProto {}

}

