package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.data.KeyContractProto;
import com.hayden.proto.datasource_proto.data.KeyValueContractProto;
import com.hayden.proto.datasource_proto.data.ValueContractProto;

public interface RequestHeaderContractProto<V extends ValueContractProto> extends
        RequestConstructContractProto,
        KeyValueContractProto<KeyContractProto.PermittingString, ValueContractProto.PermittingManyValues<V>> {

    interface AuthenticationHeaderContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingString> {
        interface BasicAuthenticationHeaderContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingString> {}
        interface BearerAuthenticationHeaderContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingString> {}
    }

    interface SessionRequestHeaderContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingString> {
    }

    interface ContentNegotiationHeaderContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingDiscrete<KeyContractProto.PermittingString>> {}
    interface CacheControlContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingDiscrete<ValueContractProto.PermittingString>> {}
    interface RangeContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}
    interface LastModifiedContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}
    interface AcceptLanguageContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}
    interface AcceptEncodingContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}
    interface ContentLengthContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingNumber> {}
    interface ContentDispositionContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}
    interface HostContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}
    interface ReferrerContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}
    interface OriginContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {}

    interface ETagContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingPattern> {
        interface IfMatchContractProto extends ETagContractProto {}
        interface IfNoneMatchContractProto extends ETagContractProto {}
        interface IfModifiedSinceContractProto extends ETagContractProto {}
        interface IfNoneModifiedSinceContractProto extends ETagContractProto {}
    }

}