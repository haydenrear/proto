package com.hayden.proto.datasource_proto.inputs.request;

import com.hayden.proto.datasource_proto.data.KeyContractProto;
import com.hayden.proto.datasource_proto.data.KeyValueContractProto;
import com.hayden.proto.datasource_proto.data.MultiValueKeyValueContractProto;
import com.hayden.proto.datasource_proto.data.ValueContractProto;
import com.hayden.proto.datasource_proto.data.value.LiteralContractProto;
import com.hayden.proto.datasource_proto.data.value.StringContractProto;

public interface RequestHeaderContractProto<KV extends KeyValueContractProto<MK, MV>, MV extends ValueContractProto, MK extends KeyContractProto, K extends KeyContractProto>
        extends
            RequestConstructContractProto,
            MultiValueKeyValueContractProto<KV, MV, MK, K> {

    enum AuthHeaders {
        BASIC, JWT, BEARER
    }

    interface AuthenticationHeaderKeyValue extends
            KeyValueContractProto<KeyContractProto.PermittingLiteral<AuthenticationType, AuthHeaders>, ValueContractProto.PermittingString> {}

    record AuthenticationHeaderKey() implements LiteralContractProto<String> {

        @Override
        public String value() {
            return "Authorization";
        }
    }

    record AuthenticationType(AuthHeaders value) implements LiteralContractProto<AuthHeaders> {
    }

    interface AuthenticationHeaderContractProto
            extends RequestHeaderContractProto<
                    AuthenticationHeaderKeyValue,
                    ValueContractProto.PermittingString,
                    KeyContractProto.PermittingLiteral<AuthenticationType, AuthHeaders>,
                    KeyContractProto.PermittingLiteral<AuthenticationHeaderKey, String>
            > {

        record BasicAuthenticationHeaderContractProto() implements AuthenticationHeaderContractProto {
            @Override
            public KeyContractProto.PermittingLiteral<AuthenticationHeaderKey, String> key() {
                return new KeyContractProto.PermittingLiteral<>(new AuthenticationHeaderKey());
            }

            @Override
            public ValueContractProto.PermittingKeyValue<AuthenticationHeaderKeyValue, KeyContractProto.PermittingLiteral<AuthenticationType, AuthHeaders>, ValueContractProto.PermittingString> value() {
                return new ValueContractProto.PermittingKeyValue<>(new AuthenticationHeaderKeyValue() {
                    @Override
                    public KeyContractProto.PermittingLiteral<AuthenticationType, AuthHeaders> key() {
                        return new KeyContractProto.PermittingLiteral<>(new AuthenticationType(AuthHeaders.BASIC));
                    }

                    @Override
                    public ValueContractProto.PermittingString value() {
                        return new ValueContractProto.PermittingString(StringContractProto.EMPTY);
                    }
                });
            }

        }

//        interface BearerAuthenticationHeaderContractProto extends RequestHeaderContractProto<ValueContractProto.PermittingString> {}
    }

    interface SessionRequestHeaderContractProto extends RequestHeaderContractProto {
    }

    interface ContentNegotiationHeaderContractProto extends RequestHeaderContractProto {}
    interface CacheControlContractProto extends RequestHeaderContractProto {}
    interface RangeContractProto extends RequestHeaderContractProto {}
    interface LastModifiedContractProto extends RequestHeaderContractProto {}
    interface AcceptLanguageContractProto extends RequestHeaderContractProto {}
    interface AcceptEncodingContractProto extends RequestHeaderContractProto {}
    interface ContentLengthContractProto extends RequestHeaderContractProto {}
    interface ContentDispositionContractProto extends RequestHeaderContractProto {}
    interface HostContractProto extends RequestHeaderContractProto {}
    interface ReferrerContractProto extends RequestHeaderContractProto {}
    interface OriginContractProto extends RequestHeaderContractProto {}

    interface ETagContractProto extends RequestHeaderContractProto {
        interface IfMatchContractProto extends ETagContractProto {}
        interface IfNoneMatchContractProto extends ETagContractProto {}
        interface IfModifiedSinceContractProto extends ETagContractProto {}
        interface IfNoneModifiedSinceContractProto extends ETagContractProto {}
    }

}