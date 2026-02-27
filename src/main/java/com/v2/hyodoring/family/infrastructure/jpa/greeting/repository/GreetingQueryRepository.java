package com.v2.hyodoring.family.infrastructure.jpa.greeting.repository;

import com.v2.hyodoring.family.infrastructure.jpa.greeting.domain.GreetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingQueryRepository extends JpaRepository<GreetingEntity,Long> {
}
