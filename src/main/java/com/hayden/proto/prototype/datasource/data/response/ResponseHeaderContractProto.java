package com.hayden.proto.prototype.datasource.data.response;

import com.hayden.proto.prototype.datasource.data.KeyContractProto;
import com.hayden.proto.prototype.datasource.data.KeyValueContractProto;
import com.hayden.proto.prototype.datasource.data.MultiValueKeyValueContractProto;
import com.hayden.proto.prototype.datasource.data.ValueContractProto;
import com.hayden.proto.prototype.value.DiscreteContractProto;

public non-sealed interface ResponseHeaderContractProto
                <KV extends KeyValueContractProto<KeyContractProto.PermittingDiscrete, MV>, MV extends ValueContractProto, K extends KeyContractProto>
        extends
                ResponseConstructContractProto,
                MultiValueKeyValueContractProto<KV, MV, KeyContractProto.PermittingDiscrete, K> {

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

    /**
     * Should set to lax so browser excludes cookie for cross-origin request
      */
    interface SameSiteHeaderContractProto extends ResponseHeaderContractProto {}

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

    record CookieResponseAttributes() implements KeyValueContractProto<KeyContractProto.PermittingDiscrete, ValueContractProto.PermittingDiscrete> {

        @Override
        public KeyContractProto.PermittingDiscrete key() {
            return new KeyContractProto.PermittingDiscrete(new CookieResponseKeys());
        }

        @Override
        public ValueContractProto.PermittingDiscrete value() {
            return new ValueContractProto.PermittingDiscrete(new CookieResponseValues());
        }

        public interface CookieResponseAttributeItem<T extends Enum<T>> {

            String key();

            T[] value();


            record SameSite() implements CookieResponseAttributeItem<SameSite.SameSiteChoices> {

                public enum SameSiteChoices {
                    NONE("none"), LAX("lax"), STRICT("strict");

                    final String value;

                    SameSiteChoices(String strict) {
                        this.value = strict;
                    }
                }

                @Override
                public String key() {
                    return "SameSite";
                }

                @Override
                public SameSiteChoices[] value() {
                    return SameSiteChoices.values();
                }
            }

        }

        record CookieResponseKeys(CookieResponseAttributeItem[] ofMany)
                implements DiscreteContractProto<CookieResponseAttributeItem> {
            public CookieResponseKeys() {
                this(new CookieResponseAttributeItem[]{});
            }
        }

        record CookieResponseValues(CookieResponseAttributeItem[] ofMany)
                implements DiscreteContractProto<CookieResponseAttributeItem> {
            public CookieResponseValues() {
                this(new CookieResponseAttributeItem[]{});
            }
        }

    }

    interface SessionResponseHeaderContractProto
            extends ResponseHeaderContractProto<CookieResponseAttributes, ValueContractProto.PermittingDiscrete, KeyContractProto.PermittingDiscrete> {
    }
}
