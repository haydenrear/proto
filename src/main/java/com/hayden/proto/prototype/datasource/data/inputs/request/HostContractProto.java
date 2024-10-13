package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.patterns.RegexPatternContractProto;
import com.hayden.proto.prototype.Permitting;

public sealed interface HostContractProto extends RequestConstructContractProto
        permits
            HostContractProto.PermitsRegexPatternProto {

    record PermitsRegexPatternProto(RegexPatternContractProto permitting)
            implements Permitting<RegexPatternContractProto>, HostContractProto {}

}

