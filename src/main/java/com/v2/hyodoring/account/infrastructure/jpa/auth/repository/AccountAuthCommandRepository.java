package com.v2.hyodoring.account.infrastructure.jpa.auth.repository;

import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AccountAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAuthCommandRepository extends JpaRepository<AccountAuthEntity, Long> {
}
