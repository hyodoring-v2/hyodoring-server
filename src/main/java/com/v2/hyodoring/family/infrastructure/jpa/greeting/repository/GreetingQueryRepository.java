package com.v2.hyodoring.family.infrastructure.jpa.greeting.repository;

import com.v2.hyodoring.family.core.greeting.GreetingType;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.domain.GreetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GreetingQueryRepository extends JpaRepository<GreetingEntity,Long> {
    Optional<GreetingEntity> findByIdAndType(Long id, GreetingType greetingType);
    List<GreetingEntity> findAllByFamilyIdAndReceiverIdAndTypeAndCreatedAtAfter(
            Long familyId, Long receiverId, GreetingType type, LocalDateTime createdAt
    );
    List<GreetingEntity> findAllByFamilyIdAndType(Long familyId, GreetingType type);
}
