package com.v2.hyodoring.account.infrastructure.jpa.role.repository;

import com.v2.hyodoring.account.core.role.GranteeType;
import com.v2.hyodoring.account.infrastructure.jpa.role.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleQueryRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByAccountIdAndFamilyIdAndGranteeType(Long accountId, Long familyId, GranteeType granteeType);
}
