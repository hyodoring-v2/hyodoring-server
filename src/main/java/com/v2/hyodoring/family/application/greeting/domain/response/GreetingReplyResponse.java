package com.v2.hyodoring.family.application.greeting.domain.response;

import com.v2.hyodoring.family.core.family.FamilyMember;
import com.v2.hyodoring.family.core.family.FamilyMemberPreview;
import com.v2.hyodoring.family.core.greeting.GreetingReply;
import com.v2.hyodoring.family.core.greeting.GreetingReplyImage;
import com.v2.hyodoring.family.core.greeting.GreetingReplyImagePreview;
import com.v2.hyodoring.family.core.greeting.GreetingTimeFormatter;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GreetingReplyResponse {
    private final Long replyId;
    private final Long familyId;
    private final FamilyMemberPreview sender;
    private final FamilyMemberPreview receiver;
    private final String createdAt;
    private final String content;
    private final List<GreetingReplyImagePreview> images;

    private GreetingReplyResponse(Long replyId, Long familyId, FamilyMemberPreview sender, FamilyMemberPreview receiver,
                                  String createdAt, String content, List<GreetingReplyImagePreview> images) {
        Assert.notNull(replyId, "replyId must not be null");
        Assert.notNull(familyId, "familyId must not be null");
        Assert.notNull(sender, "sender must not be null");
        Assert.notNull(receiver, "receiver must not be null");
        Assert.hasText(createdAt, "createdAt must not be empty");
        Assert.hasText(content, "content must not be empty");
        Assert.notNull(images, "images must not be null");
        this.replyId = replyId;
        this.familyId = familyId;
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = createdAt;
        this.content = content;
        this.images = images;
    }

    public static GreetingReplyResponse of(GreetingReply greetingReply,
                                           FamilyMember sender,
                                           FamilyMember receiver,
                                           List<GreetingReplyImage> images) {
        return new GreetingReplyResponse(
                greetingReply.getId(),
                greetingReply.getFamilyId(),
                FamilyMemberPreview.of(sender.getAccountId(), sender.getNickname(), sender.getRole()),
                FamilyMemberPreview.of(receiver.getAccountId(), receiver.getNickname(), receiver.getRole()),
                GreetingTimeFormatter.format(Duration.between(greetingReply.getCreatedAt(), LocalDateTime.now())),
                greetingReply.getContent(),
                images.stream()
                        .map(image -> GreetingReplyImagePreview.of(image.getId(), image.getUrl()))
                        .toList()
        );
    }
}
