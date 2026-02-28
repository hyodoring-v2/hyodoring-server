package com.v2.hyodoring.account.application.account;

import com.v2.hyodoring.account.application.auth.AuthQueryService;
import com.v2.hyodoring.account.core.auth.domain.AccountOAuthData;
import com.v2.hyodoring.account.infrastructure.jpa.account.domain.AccountEntity;
import com.v2.hyodoring.account.presentation.account.domain.AccountProfileResponse;
import com.v2.hyodoring.family.core.family.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountApiQueryService {
    private final AccountQueryService accountQueryService;
    private final AuthQueryService authQueryService;

    /**
     * 계정 프로필 조회
     * - 계정 기본 정보 (닉네임, 가입일자, 레벨)
     * - 연결된 OAuth 인증 정보 (인증 제공자, 이메일)
     * - 가족 정보 (가족 이름, 역할)
     * @param accountId 계정 ID
     * @return 계정 프로필 정보 반환
     */
    public AccountProfileResponse getProfile(Long accountId) {
        AccountEntity accountEntity = accountQueryService.getAccount(accountId);
        AccountOAuthData accountOAuthData = authQueryService.getAllAccountOAuthData(accountId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No OAuth data found for accountId: " + accountId));
        return AccountProfileResponse.of(
                accountEntity.getNickname(),
                Level.fromScore(accountEntity.getScore()),
                accountEntity.getCreatedAt(),
                accountOAuthData.getAuthProvider(),
                accountOAuthData.getEmail(),
                List.of() // TODO: 가족 정보 추가
        );
    }
}
