package com.v2.hyodoring.family.infrastructure.jpa.notification.repository;

import com.v2.hyodoring.family.infrastructure.jpa.notification.domain.FCMTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FCMTokenCommandRepository extends JpaRepository<FCMTokenEntity, Long> {
}
