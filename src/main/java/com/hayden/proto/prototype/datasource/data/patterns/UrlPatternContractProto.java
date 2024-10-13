package com.hayden.proto.prototype.datasource.data.patterns;


import com.hayden.proto.prototype.Permitting;
import com.hayden.proto.prototype.Prototype;

public sealed interface UrlPatternContractProto extends Prototype
    permits UrlPatternContractProto.PermittingUrlPatternContractProto {

    non-sealed interface PermittingUrlPatternContractProto<T extends UrlPatternContractProto>
            extends Permitting<T>, UrlPatternContractProto { }

}
