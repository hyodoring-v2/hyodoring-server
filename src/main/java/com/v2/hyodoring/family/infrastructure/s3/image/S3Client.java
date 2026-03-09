package com.v2.hyodoring.family.infrastructure.s3.image;

import com.v2.hyodoring.family.core.image.ImageType;
import com.v2.hyodoring.family.infrastructure.s3.image.domain.S3ObjectUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Client {
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3ObjectUrl createPresignedUrl(ImageType type, String extension) {
        // S3 Key 생성
        final String keyName = createS3Key(type);

        // presignRequest 생성
        final PutObjectPresignRequest presignRequest = createPutObjectPresignRequest(keyName, extension);

        // presigned URL과 Public URL 발급
        final PresignedPutObjectRequest request = s3Presigner.presignPutObject(presignRequest);
        return S3ObjectUrl.of(request.url().toString(), keyName);

    }

    private PutObjectPresignRequest createPutObjectPresignRequest(String keyName, String extension) {
        final String contentType = "image/" + extension;
        final Map<String, String> metadata = Map.of(
                "fileType", contentType,
                "Content-Type", contentType
        );

        return PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(keyName)
                        .metadata(metadata)
                        .build())
                .build();
    }

    private static String createS3Key(ImageType type) {
        return type.toString().toLowerCase() + "/" + UUID.randomUUID().toString().replaceAll("-", "");
    }
}
