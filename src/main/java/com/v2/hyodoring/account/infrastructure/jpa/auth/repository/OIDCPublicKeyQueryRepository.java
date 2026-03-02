package com.v2.hyodoring.account.infrastructure.jpa.auth.repository;

import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.OIDCPublicKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OIDCPublicKeyQueryRepository extends JpaRepository<OIDCPublicKeyEntity, Long> {
    List<OIDCPublicKeyEntity> findByProviderId(Long providerId);
}
