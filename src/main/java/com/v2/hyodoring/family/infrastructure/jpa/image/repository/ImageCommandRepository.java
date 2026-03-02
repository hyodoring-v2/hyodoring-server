package com.v2.hyodoring.family.infrastructure.jpa.image.repository;

import com.v2.hyodoring.family.infrastructure.jpa.image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageCommandRepository extends JpaRepository<ImageEntity,Long> {
}
