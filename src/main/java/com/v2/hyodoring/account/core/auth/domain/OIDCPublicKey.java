package com.v2.hyodoring.account.core.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * OIDC Public Key 정보를 담은 클래스
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OIDCPublicKey {

    @JsonProperty("kid")
    private String kid;

    @JsonProperty("kty")
    private String kty;

    @JsonProperty("alg")
    private String alg;

    @JsonProperty("use")
    private String use;

    @JsonProperty("n")
    private String n;

    @JsonProperty("e")
    private String e;

    public static OIDCPublicKey of(
            String kid,
            String kty,
            String alg,
            String use,
            String n,
            String e
    ) {
        return new OIDCPublicKey(kid, kty, alg, use, n, e);
    }
}
