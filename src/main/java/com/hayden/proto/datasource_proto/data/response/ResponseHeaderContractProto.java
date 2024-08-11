package com.hayden.proto.datasource_proto.data.response;

import com.hayden.proto.datasource_proto.data.KeyContractProto;
import com.hayden.proto.datasource_proto.data.KeyValueContractProto;
import com.hayden.proto.datasource_proto.data.ValueContractProto;

public non-sealed interface ResponseHeaderContractProto<V extends ValueContractProto> extends
        ResponseConstructContractProto,
        KeyValueContractProto<KeyContractProto.PermittingString, ValueContractProto.PermittingManyValues<V>> {

    interface DateHeaderContractProto extends ResponseHeaderContractProto {}

    interface LocationHeaderContractProto extends ResponseHeaderContractProto {}
    interface RetryAfterHeaderContractProto extends ResponseHeaderContractProto {}
    interface ServerHeaderContractProto extends ResponseHeaderContractProto {}
    interface VaryHeaderContractProto extends ResponseHeaderContractProto {}

    interface ContentDispositionHeaderContractProto extends ResponseHeaderContractProto {}
    interface ContentEncodingHeaderContractProto extends ResponseHeaderContractProto {}
    interface ContentLanguageHeaderContractProto extends ResponseHeaderContractProto {}
    interface ContentLengthHeaderContractProto extends ResponseHeaderContractProto {}
    interface ContentLocationHeaderContractProto extends ResponseHeaderContractProto {}
    interface ContentRangeHeaderContractProto extends ResponseHeaderContractProto {}
    interface ContentTypeHeaderContractProto extends ResponseHeaderContractProto {}

    interface CacheControlHeaderContractProto extends ResponseHeaderContractProto {}
    interface ExpiresHeaderContractProto extends ResponseHeaderContractProto {}
    interface LastModifiedHeaderContractProto extends ResponseHeaderContractProto {}

    interface ETagHeaderContractProto extends ResponseHeaderContractProto {
        interface IfMatchHeaderContractProto extends ETagHeaderContractProto {}
        interface IfNoneMatchHeaderContractProto extends ETagHeaderContractProto {}
        interface IfModifiedSinceHeaderContractProto extends ETagHeaderContractProto {}
        interface IfNoneModifiedSinceHeaderContractProto extends ETagHeaderContractProto {}
    }

    interface WWWAuthenticateHeaderContractProto extends ResponseHeaderContractProto {
        interface BasicAuthenticationHeaderContractProto extends WWWAuthenticateHeaderContractProto {}
        interface DigestAuthenticationHeaderContractProto extends WWWAuthenticateHeaderContractProto {}
        interface BearerAuthenticationHeaderContractProto extends WWWAuthenticateHeaderContractProto {}
    }

    interface SessionResponseHeaderContractProto extends ResponseHeaderContractProto {}
}
