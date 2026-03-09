package com.v2.hyodoring.family.infrastructure.s3.image.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class S3ObjectUrl {
    private final String presignedUrl;
    private final String publicUrl;

    private S3ObjectUrl(String presignedUrl, String publicUrl) {
        Assert.hasText(presignedUrl, "presignedUrl must not be empty");
        Assert.hasText(publicUrl, "publicUrl must not be empty");
        this.presignedUrl = presignedUrl;
        this.publicUrl = publicUrl;
    }

    public static S3ObjectUrl of(String presignedUrl, String publicUrl) {
        return new S3ObjectUrl(presignedUrl, publicUrl);
    }
}
