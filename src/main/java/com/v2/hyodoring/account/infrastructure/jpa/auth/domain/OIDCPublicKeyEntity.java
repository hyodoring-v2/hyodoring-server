package com.v2.hyodoring.account.infrastructure.jpa.auth.domain;

import com.v2.hyodoring.account.core.auth.domain.OIDCPublicKey;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity(name = "oidc_public_key")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OIDCPublicKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long providerId;

    @Column(columnDefinition = "text", nullable = false)
    private String kid;

    @Column(columnDefinition = "text", nullable = false)
    private String kty;

    @Column(columnDefinition = "text", nullable = false)
    private String alg;

    @Column(columnDefinition = "text", nullable = false)
    private String sigUse;

    @Column(columnDefinition = "text", nullable = false)
    private String n;

    @Column(columnDefinition = "text", nullable = false)
    private String e;

    public OIDCPublicKey toVo() {
        return OIDCPublicKey.of(kid, kty, alg, sigUse, n, e);
    }

    public static OIDCPublicKeyEntity of(Long providerId, OIDCPublicKey key) {
        return OIDCPublicKeyEntity.builder()
                .providerId(providerId)
                .kid(key.getKid())
                .kty(key.getKty())
                .alg(key.getAlg())
                .sigUse(key.getUse())
                .n(key.getN())
                .e(key.getE())
                .build();
    }
}
