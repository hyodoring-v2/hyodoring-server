package com.v2.hyodoring.family.application.image.service;

import com.v2.hyodoring.family.core.image.ImageType;
import com.v2.hyodoring.family.infrastructure.s3.image.S3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3ImageService {
    private final S3Client s3Client;

    public String getPresignedUrl(ImageType imageType, String extension) {
        return s3Client.createPresignedUrl(imageType, extension).getPresignedUrl();
    }
}
