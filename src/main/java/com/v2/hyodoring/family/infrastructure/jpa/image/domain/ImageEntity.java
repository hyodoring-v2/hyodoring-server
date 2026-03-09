package com.v2.hyodoring.family.infrastructure.jpa.image.domain;

import com.v2.hyodoring.family.core.greeting.GreetingReplyImage;
import com.v2.hyodoring.family.core.image.ImageType;
import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Entity(name = "image")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType targetType;

    @Column(nullable = false)
    private String url;

    public static ImageEntity from(GreetingReplyImage image) {
        return ImageEntity.builder()
                .targetId(image.getGreetingId())
                .targetType(ImageType.GREETING_IMAGE)
                .url(image.getUrl())
                .build();
    }

    public GreetingReplyImage toGreetingReplyImage() {
        if (!ImageType.GREETING_IMAGE.equals(targetType)) {
            throw new IllegalArgumentException("targetType must be greeting image");
        }
        return GreetingReplyImage.of(id, targetId, url);
    }

    public static ImageEntity of(long targetId, ImageType targetType, String url) {
        Assert.notNull(targetType, "targetType can not be null");
        Assert.hasText(url, "url must not be empty");
        return ImageEntity.builder()
                .targetId(targetId)
                .targetType(targetType)
                .url(url)
                .build();
    }
}
