package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.core.auth.domain.AccountOAuth;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AccountOAuthEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AccountOAuthCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCommandService {
    private final AccountOAuthCommandRepository accountOAuthCommandRepository;

    public AccountOAuth save(AccountOAuth accountOAuth) {
        return accountOAuthCommandRepository.save(AccountOAuthEntity.from(accountOAuth)).toDomain();
    }
}
