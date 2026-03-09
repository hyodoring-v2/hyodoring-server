package com.v2.hyodoring.family.application.image.service;

import com.v2.hyodoring.family.core.greeting.GreetingReplyImage;
import com.v2.hyodoring.family.core.image.ImageType;
import com.v2.hyodoring.family.infrastructure.jpa.image.domain.ImageEntity;
import com.v2.hyodoring.family.infrastructure.jpa.image.repository.ImageCommandRepository;
import com.v2.hyodoring.family.infrastructure.jpa.image.repository.ImageQueryRepository;
import com.v2.hyodoring.family.infrastructure.s3.image.S3ImageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageCommandService {
    private final S3ImageClient s3ImageClient;

    private final ImageCommandRepository imageCommandRepository;
    private final ImageQueryRepository imageQueryRepository;

    public String getPresignedUrl(ImageType imageType, String extension) {
        return s3ImageClient.createPresignedUrl(imageType, extension).getPresignedUrl();
    }

    public List<GreetingReplyImage> uploadGreetingImages(Long greetingId, List<String> imageUrls) {
        final List<ImageEntity> imageEntities = imageCommandRepository.saveAll(imageUrls.stream()
                .map(url -> ImageEntity.from(GreetingReplyImage
                        .create(greetingId, url)))
                .toList());
        return imageEntities.stream()
                .map(ImageEntity::toGreetingReplyImage)
                .toList();
    }
}
