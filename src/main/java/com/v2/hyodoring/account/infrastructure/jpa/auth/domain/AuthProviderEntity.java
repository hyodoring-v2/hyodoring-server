package com.v2.hyodoring.account.infrastructure.jpa.auth.domain;

import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.core.shared.domain.Env;
import com.v2.hyodoring.account.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity(name = "auth_provider")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_auth_provider_name_env", columnNames = {"name", "env"})
        }
)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthProviderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Env env;

    @Column(columnDefinition = "text", nullable = false)
    private String clientId;

    @Column(columnDefinition = "text", nullable = false)
    private String clientSecret;

    @Column(columnDefinition = "text", nullable = false)
    private String redirectUri;

    public AuthProvider toDomain() {
        return AuthProvider.of(id, name, env);
    }
}
