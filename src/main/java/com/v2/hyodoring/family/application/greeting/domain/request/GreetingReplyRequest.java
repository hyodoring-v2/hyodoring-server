package com.v2.hyodoring.family.application.greeting.domain.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GreetingReplyRequest {
    private final Long familyId;
    private final Long requestId;
    private final Long senderId;
    private final Long receiverId;
    private final String content;
    private final List<String> imageUrls;
}
