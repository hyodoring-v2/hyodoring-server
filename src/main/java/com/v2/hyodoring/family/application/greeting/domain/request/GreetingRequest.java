package com.v2.hyodoring.family.application.greeting.domain.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GreetingRequest {
    private final Long familyId;
    private final Long senderId;
    private final Long receiverId;
    private final String content;
}
