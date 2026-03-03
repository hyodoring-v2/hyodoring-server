package com.v2.hyodoring.account.application.account.service;

import com.v2.hyodoring.account.application.account.domain.exception.AccountErrorResponse;
import com.v2.hyodoring.account.application.account.domain.exception.AccountException;
import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.core.role.AccountRoleType;
import com.v2.hyodoring.account.core.role.GranteeType;
import com.v2.hyodoring.account.infrastructure.jpa.account.domain.AccountEntity;
import com.v2.hyodoring.account.infrastructure.jpa.account.repository.AccountQueryRepository;
import com.v2.hyodoring.account.infrastructure.jpa.role.repository.RoleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;

    private final RoleQueryRepository roleQueryRepository;

    public Account getAccount(Long accountId) {
        return accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(AccountErrorResponse.ACCOUNT_NOT_FOUND))
                .toDomain();
    }

    public Account getActiveAccount(Long accountId) {
        final AccountEntity account = accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(AccountErrorResponse.ACCOUNT_NOT_FOUND));
        if (account.isInactive()) {
            throw new AccountException(AccountErrorResponse.ACCOUNT_NOT_FOUND);
        }
        return account.toDomain();
    }

    public AccountRoleType getAccountRole(Long accountId) {
        return roleQueryRepository.findByGranteeIdAndGranteeType(accountId, GranteeType.ACCOUNT)
                .orElseThrow(() -> new AccountException(AccountErrorResponse.ACCOUNT_ROLE_NOT_FOUND))
                .toAccountRole()
                .getName();
    }
}
