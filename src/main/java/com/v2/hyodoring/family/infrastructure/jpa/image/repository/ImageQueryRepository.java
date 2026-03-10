package com.v2.hyodoring.family.infrastructure.jpa.image.repository;

import com.v2.hyodoring.family.core.image.ImageType;
import com.v2.hyodoring.family.infrastructure.jpa.image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageQueryRepository extends JpaRepository<ImageEntity,Long> {
    List<ImageEntity> findAllByTargetIdAndTargetType(Long targetId, ImageType targetType);

}
