package com.v2.hyodoring.family.application.greeting.domain.response;

import com.v2.hyodoring.family.core.greeting.GreetingMessage;

import java.util.List;

public record GreetingRequestListResponse(
        Long familyId,
        List<GreetingMessage> greetings
) {
}