package com.v2.hyodoring.account.infrastructure.jpa.auth.repository;

import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AccountOAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountOAuthCommandRepository extends JpaRepository<AccountOAuthEntity,Long> {
}
