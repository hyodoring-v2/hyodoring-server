package com.v2.hyodoring.account.infrastructure.jpa.auth.domain;

import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.core.shared.domain.Environment;
import com.v2.hyodoring.account.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

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
    private AuthProvider name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Environment env;

    @Column(columnDefinition = "text", nullable = false)
    private String clientId;

    @Column(columnDefinition = "text", nullable = false)
    private String redirectUri;

    public static AuthProviderEntity of(AuthProvider name, Environment env, String clientId, String redirectUri) {
        Assert.notNull(name, "name can not be null");
        Assert.notNull(env, "env can not be null");
        Assert.hasText(clientId, "clientId must not be empty");
        Assert.hasText(redirectUri, "redirectUri must not be empty");
        return AuthProviderEntity.builder()
                .name(name)
                .env(env)
                .clientId(clientId)
                .redirectUri(redirectUri)
                .build();
    }
}
