package com.v2.hyodoring.family.infrastructure.jpa.notification.repository;

import com.v2.hyodoring.family.core.notification.DeviceType;
import com.v2.hyodoring.family.infrastructure.jpa.notification.domain.FCMTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FCMTokenQueryRepository extends JpaRepository<FCMTokenEntity, Long> {
    Optional<FCMTokenEntity> findByAccountIdAndDeviceType(Long accountId, DeviceType deviceType);
}
