package com.v2.hyodoring.family.infrastructure.jpa.notification.repository;

import com.v2.hyodoring.family.infrastructure.jpa.notification.domain.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationQueryRepository extends JpaRepository<NotificationEntity,Long> {
}
