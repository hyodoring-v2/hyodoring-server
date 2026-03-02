package com.v2.hyodoring.account.infrastructure.jpa.auth.repository;

import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.OIDCPublicKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OIDCPublicKeyCommandRepository extends JpaRepository<OIDCPublicKeyEntity, Long> {
}
