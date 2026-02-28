package com.v2.hyodoring.account.infrastructure.jpa.auth.repository;

import com.v2.hyodoring.account.core.shared.domain.Env;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AuthProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthProviderQueryRepository extends JpaRepository<AuthProviderEntity,Long> {
    Optional<AuthProviderEntity> findByIdAndEnv(Long id, Env env);
}
