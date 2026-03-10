package com.v2.hyodoring.family.application.greeting.domain.response;

import java.util.List;

public record GreetingReplyListResponse(
        Long familyId,
        List<GreetingReplyResponse> replyList
) {
}
