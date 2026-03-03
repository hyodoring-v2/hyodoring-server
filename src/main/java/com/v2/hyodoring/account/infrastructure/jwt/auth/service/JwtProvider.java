package com.v2.hyodoring.account.infrastructure.jwt.auth.service;

import com.v2.hyodoring.account.application.auth.domain.exception.AuthErrorResponse;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthException;
import com.v2.hyodoring.account.core.role.AccountRoleType;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final String prefix;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
        this.prefix = jwtProperties.prefix();
        this.accessTokenExpirationMs = jwtProperties.accessToken().expirationMs();
        this.refreshTokenExpirationMs = jwtProperties.refreshToken().expirationMs();
    }

    /**
     * 액세스 토큰을 생성하는 메서드
     * @param id 계정 ID
     * @param nickname 계정 닉네임
     * @param role 계정 권한 {@link AccountRoleType}
     * @return 생성된 액세스 토큰
     */
    public String generateAccessToken(long id, String nickname, AccountRoleType role) {
        return generateToken(id, nickname, role, accessTokenExpirationMs);
    }

    /**
     * 리프레시 토큰을 생성하는 메서드
     * @param id 계정 ID
     * @param nickname 계정 닉네임
     * @param role 계정 권한 {@link AccountRoleType}
     * @return 생성된 리프레시 토큰
     */
    public String generateRefreshToken(long id, String nickname, AccountRoleType role) {
        return generateToken(id, nickname, role, refreshTokenExpirationMs);
    }

    /**
     * 리프레시 토큰으로부터 새로운 액세스 토큰을 발급하는 메서드
     * @param refreshToken 리프레시 토큰 (JWT)
     * @return 접두사 + 생성된 액세스 토큰
     */
    public String reissueAccessToken(String refreshToken) {
        try {
            Claims claims = getClaims(refreshToken);
            return String.format("%s %s", prefix, generateAccessToken(
                    Long.parseLong(claims.getSubject()),
                    claims.get("nickname", String.class),
                    AccountRoleType.valueOf(claims.get("role", String.class))
            ));
        } catch (Exception e) {
            throw new AuthException(AuthErrorResponse.JWT_REFRESH_FAILED);
        }
    }

    /**
     * 토큰에서 접두사를 제거하기 위한 메서드
     * @param token 접두사가 포함된 토큰 헤더 값
     * @return 토큰 값이 있는 경우 토큰 반환, 토큰 값이 없는 경우 null 반환
     */
    public String resolveToken(String token) {
        if (!StringUtils.hasText(token) || !token.toLowerCase().startsWith(prefix.toLowerCase())) {
            return null;
        }
        final String resolvedToken = token.substring(prefix.length()).trim();
        return StringUtils.hasText(resolvedToken) ? resolvedToken : null;
    }

    /**
     * 주어진 토큰을 검증하는 메서드
     * @param token JWT (액세스 or 리프레시 토큰)
     * @return 토큰의 유효 여부
     */
    public boolean isValidToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰을 통해 Principal을 생성하는 메서드
     * @param token JWT
     * @return JWT로 생성한 계정의 Principal
     */
    public AccountPrincipal generatePrincipal(String token) {
        try {
            final Claims claims = getClaims(token);
            return AccountPrincipal.of(
                    Long.parseLong(claims.getSubject()),
                    claims.get("nickname", String.class),
                    AccountRoleType.valueOf(claims.get("role", String.class))
            );
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    /**
     * Jwts 빌더를 통해 JWT를 생성하는 메서드
     * @return 생성된 JWT
     */
    private String generateToken(long id, String nickname, AccountRoleType role, long expirationMs) {
        Assert.hasText(nickname, "nickname must not be empty");
        Assert.notNull(role, "role must not be null");

        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("nickname", nickname)
                .claim("role", role.toString())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 토큰으로부터 Claim을 조회하는 메서드
     * @param token JWT
     * @return JWT에 포함된 모든 Claims
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
