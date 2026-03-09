package com.v2.hyodoring.family.core.greeting;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class GreetingReplyImagePreview {
    private final Long imageId;
    private final String url;

    private GreetingReplyImagePreview(Long imageId, String url) {
        Assert.notNull(imageId, "imageId must not be null");
        Assert.hasText(url, "url must not be empty");
        this.imageId = imageId;
        this.url = url;
    }

    public static GreetingReplyImagePreview of(Long imageId, String url) {
        return new GreetingReplyImagePreview(imageId, url);
    }
}
