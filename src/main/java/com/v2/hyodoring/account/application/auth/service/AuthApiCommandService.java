package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.application.account.service.AccountCommandService;
import com.v2.hyodoring.account.application.account.service.AccountQueryService;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthErrorResponse;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthException;
import com.v2.hyodoring.account.application.auth.domain.response.AccountTokenResponse;
import com.v2.hyodoring.account.application.auth.service.oidc.OIDCService;
import com.v2.hyodoring.account.application.auth.service.oidc.OIDCServiceFactory;
import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.core.auth.domain.AccountOAuth;
import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.core.role.AccountRoleType;
import com.v2.hyodoring.account.infrastructure.feign.auth.domain.OIDCPayload;
import com.v2.hyodoring.account.infrastructure.jwt.auth.service.JwtProvider;
import com.v2.hyodoring.family.application.family.service.FamilyCommandService;
import com.v2.hyodoring.family.application.family.service.FamilyQueryService;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.core.family.FamilyRole;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthApiCommandService {
    private final OIDCServiceFactory oidcServiceFactory;
    private final JwtProvider jwtProvider;

    private final AuthCommandService authCommandService;
    private final AuthQueryService authQueryService;
    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;

    private final FamilyCommandService familyCommandService;
    private final FamilyQueryService familyQueryService;

    public AccountTokenResponse signIn(Provider provider, String idToken) {
        // 소셜 로그인
        final OIDCService oidcService = oidcServiceFactory.getOIDCService(provider);
        OIDCPayload payload = oidcService.signIn(idToken);

        // subject를 통한 계정 조회
        final Account account = authQueryService.getActiveAccountBySubject(provider, payload.getSubject());
        final AccountRoleType accountRole = accountQueryService.getAccountRole(account.getId());

        // JWT 액세스 토큰 및 리프레시 토큰 발급
        final String accessToken = jwtProvider.generateAccessToken(account.getId(), account.getNickname(), accountRole);
        final String refreshToken = jwtProvider.generateRefreshToken(account.getId(), account.getNickname(), accountRole);
        return AccountTokenResponse.of(
                account.getId(),
                account.getNickname(),
                accessToken,
                refreshToken
        );
    }

    public AccountTokenResponse signUp(Provider provider, String idToken, String familyCode, FamilyRoleType role) {
        // id token 검증
        final OIDCService oidcService = oidcServiceFactory.getOIDCService(provider);
        OIDCPayload payload = oidcService.verify(idToken);

        // 활성화된 계정이 이미 존재하는 경우 회원가입 불가
        if (authQueryService.existsActiveAccountBySubject(provider, payload.getSubject())) {
            throw new AuthException(AuthErrorResponse.ACCOUNT_ALREADY_EXISTS);
        }

        // 계정 생성
        final Account account = accountCommandService.save(Account.create(payload.getName()), AccountRoleType.USER);
        final AccountRoleType accountRole = accountQueryService.getAccountRole(account.getId());

        // JWT 액세스 토큰 및 리프레시 토큰 발급
        final String accessToken = jwtProvider.generateAccessToken(account.getId(), account.getNickname(), accountRole);
        final String refreshToken = jwtProvider.generateRefreshToken(account.getId(), account.getNickname(), accountRole);

        // 인증 정보 생성
        final AuthProvider authProvider = authQueryService.getAuthProviderByName(provider);
        authCommandService.save(AccountOAuth.create(
                account.getId(),
                authProvider.getId(),
                payload.getSubject(),
                accessToken,
                refreshToken,
                payload.getEmail()
        ));

        // 신규 가족 생성 or 기존 가족 참여
        if (familyQueryService.existsByFamilyCode(familyCode)) {
            final Family family = familyQueryService.findByFamilyCode(familyCode);
            familyCommandService.joinFamily(FamilyRole.create(account.getId(), family.getId(), role), account);
        } else {
            familyCommandService.generateFamily(Family.create(familyCode), role, account);
        }

        return AccountTokenResponse.of(
                account.getId(),
                account.getNickname(),
                accessToken,
                refreshToken
        );
    }
}
