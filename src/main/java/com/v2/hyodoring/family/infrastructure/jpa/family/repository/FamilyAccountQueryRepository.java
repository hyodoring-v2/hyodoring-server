package com.v2.hyodoring.family.infrastructure.jpa.family.repository;

import com.v2.hyodoring.family.infrastructure.jpa.family.domain.FamilyAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyAccountQueryRepository extends JpaRepository<FamilyAccountEntity,Long> {
    List<FamilyAccountEntity> findAllByFamilyId(Long familyId);
    Optional<FamilyAccountEntity> findByFamilyIdAndAccountId(Long familyId, Long accountId);
}
