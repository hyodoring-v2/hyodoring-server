package com.v2.hyodoring.account.infrastructure.feign.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.v2.hyodoring.account.core.auth.domain.OIDCPublicKey;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OIDCPublicKeyResponse {
    @JsonProperty("keys")
    private List<OIDCPublicKey> keys;
}
