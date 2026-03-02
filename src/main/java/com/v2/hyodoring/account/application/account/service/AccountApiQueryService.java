package com.v2.hyodoring.account.application.account.service;

import com.v2.hyodoring.account.application.auth.service.AuthQueryService;
import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.core.auth.domain.AccountOAuthData;
import com.v2.hyodoring.account.application.account.domain.AccountProfileResponse;
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
        Account account = accountQueryService.getActiveAccount(accountId);
        AccountOAuthData accountOAuthData = authQueryService.getAllAccountOAuthData(accountId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No OAuth data found for accountId: " + accountId));
        return AccountProfileResponse.of(
                account.getNickname(),
                Level.fromScore(50), //TODO: 가족 역할 점수 추가
                account.getCreatedAt(),
                accountOAuthData.getProvider(),
                accountOAuthData.getEmail(),
                List.of() // TODO: 가족 정보 추가
        );
    }
}
