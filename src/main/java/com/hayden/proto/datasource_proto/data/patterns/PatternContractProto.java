package com.hayden.proto.datasource_proto.data.patterns;

import com.hayden.proto.permitting.Permitting;

public sealed interface PatternContractProto
        permits
            PatternContractProto.PermittingRegexContractProto,
            PatternContractProto.PermittingAntContractProto,
            PatternContractProto.PermittingUrlPattern {

    record PermittingRegexContractProto(RegexPatternContractProto permitting)
            implements Permitting<RegexPatternContractProto>, PatternContractProto {}

    record PermittingAntContractProto(AntPatternContractProto permitting)
            implements Permitting<AntPatternContractProto>, PatternContractProto {}

    record PermittingUrlPattern(UrlPatternContractProto permitting)
            implements Permitting<UrlPatternContractProto>, PatternContractProto {}

}
