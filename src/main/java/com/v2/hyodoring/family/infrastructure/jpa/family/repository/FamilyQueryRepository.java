package com.v2.hyodoring.family.infrastructure.jpa.family.repository;

import com.v2.hyodoring.family.infrastructure.jpa.family.domain.FamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyQueryRepository extends JpaRepository<FamilyEntity,Long> {
    Optional<FamilyEntity> findByCode(String code);
    boolean existsByCode(String code);
}
