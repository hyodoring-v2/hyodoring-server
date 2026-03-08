package com.v2.hyodoring.account.application.account.service;

import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.core.account.domain.AccountRole;
import com.v2.hyodoring.account.core.role.AccountRoleType;
import com.v2.hyodoring.account.infrastructure.jpa.account.domain.AccountEntity;
import com.v2.hyodoring.account.infrastructure.jpa.account.repository.AccountCommandRepository;
import com.v2.hyodoring.account.infrastructure.jpa.role.domain.RoleEntity;
import com.v2.hyodoring.account.infrastructure.jpa.role.repository.RoleCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountCommandService {

    private final AccountCommandRepository accountCommandRepository;
    private final RoleCommandRepository roleCommandRepository;

    public Account save(Account account, AccountRoleType role) {
        // 계정 정보 저장
        final AccountEntity accountEntity = accountCommandRepository.save(AccountEntity.from(account));
        // 계정 권한 저장
        roleCommandRepository.save(RoleEntity.from(AccountRole.create(accountEntity.getId(), role)));
        return accountEntity.toDomain();
    }
}
