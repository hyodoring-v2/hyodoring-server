package com.v2.hyodoring.account.application.account.service;

import com.v2.hyodoring.account.application.auth.domain.exception.AuthException;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthErrorResponse;
import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.infrastructure.jpa.account.domain.AccountEntity;
import com.v2.hyodoring.account.infrastructure.jpa.account.repository.AccountQueryRepository;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AccountOAuthEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;


    public Account getAccount(Long accountId) {
        return accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId))
                .toDomain();
    }

    public Account getActiveAccount(Long accountId) {
        final AccountEntity account = accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));
        if (account.isInactive()) {
            throw new IllegalStateException("Account is inactive with id: " + accountId);
        }
        return account.toDomain();
    }
}
