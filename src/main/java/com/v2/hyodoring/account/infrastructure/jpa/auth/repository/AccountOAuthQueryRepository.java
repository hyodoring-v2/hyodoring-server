package com.v2.hyodoring.account.infrastructure.jpa.auth.repository;

import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AccountOAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountOAuthQueryRepository extends JpaRepository<AccountOAuthEntity,Long> {
    Optional<AccountOAuthEntity> findByAccountIdAndProviderId(Long accountId, Long providerId);
    List<AccountOAuthEntity> findAllByAccountId(Long accountId);
}
