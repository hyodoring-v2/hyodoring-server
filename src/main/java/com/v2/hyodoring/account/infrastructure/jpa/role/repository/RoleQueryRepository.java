package com.v2.hyodoring.account.infrastructure.jpa.role.repository;

import com.v2.hyodoring.account.infrastructure.jpa.role.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleQueryRepository extends JpaRepository<RoleEntity, Long> {
}
