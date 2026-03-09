package com.v2.hyodoring.family.application.greeting.domain.response;

import com.v2.hyodoring.family.core.family.FamilyMember;
import com.v2.hyodoring.family.core.family.FamilyMemberPreview;
import com.v2.hyodoring.family.core.greeting.Greeting;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class GreetingRequestResponse {
    private final Long requestId;
    private final Long familyId;
    private final FamilyMemberPreview sender;
    private final FamilyMemberPreview receiver;
    private final String title;
    private final String body;

    private GreetingRequestResponse(Long requestId, Long familyId, FamilyMemberPreview sender,
                                    FamilyMemberPreview receiver, String title, String body) {
        Assert.notNull(requestId, "requestId must not be null");
        Assert.notNull(familyId, "familyId must not be null");
        Assert.notNull(sender, "sender must not be null");
        Assert.notNull(receiver, "receiver must not be null");
        Assert.hasText(title, "title must not be empty");
        Assert.hasText(body, "body must not be empty");
        this.requestId = requestId;
        this.familyId = familyId;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.body = body;
    }

    public static GreetingRequestResponse of(Greeting greeting,
                                             FamilyMember sender,
                                             FamilyMember receiver,
                                             String title,
                                             String body) {
        return new GreetingRequestResponse(
                greeting.getId(),
                greeting.getFamilyId(),
                FamilyMemberPreview.of(sender.getAccountId(), sender.getNickname(), sender.getRole()),
                FamilyMemberPreview.of(receiver.getAccountId(), receiver.getNickname(), receiver.getRole()),
                title,
                body
        );
    }
}
