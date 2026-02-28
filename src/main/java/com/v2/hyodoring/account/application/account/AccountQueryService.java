package com.v2.hyodoring.account.application.account;

import com.v2.hyodoring.account.infrastructure.jpa.account.domain.AccountEntity;
import com.v2.hyodoring.account.infrastructure.jpa.account.repository.AccountQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;

    public AccountEntity getAccount(Long accountId) {
        final AccountEntity account = accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));
        if (account.isInactive()) {
            throw new IllegalStateException("Account is inactive with id: " + accountId);
        }
        return account;
    }
}
