package com.hayden.proto.prototype.datasource.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.patterns.RegexPatternContractProto;
import com.hayden.proto.prototype.Permitting;

public sealed interface PathContractProto extends RequestConstructContractProto
        permits
            PathContractProto.PermitsRegexPatternProto {

    record PermitsRegexPatternProto(RegexPatternContractProto permitting)
            implements Permitting<RegexPatternContractProto>, PathContractProto {}

}