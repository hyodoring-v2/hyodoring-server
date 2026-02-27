package com.v2.hyodoring.account.infrastructure.jpa.account.repository;

import com.v2.hyodoring.account.infrastructure.jpa.account.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCommandRepository extends JpaRepository<AccountEntity, Long> {
}
