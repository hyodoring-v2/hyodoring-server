package com.v2.hyodoring.family.core.greeting;

import com.v2.hyodoring.family.core.family.FamilyMember;

import java.time.Duration;
import java.time.LocalDateTime;

public record GreetingMessage(
        String title,
        String body
) {
    public static String generateBody(Greeting greeting, FamilyMember sender) {
        final Duration duration = Duration.between(greeting.getCreatedAt(), LocalDateTime.now());
        return String.format("%s %s(이)가 안부를 요청했어요",
                GreetingTimeFormatter.format(duration), // 방금 전, n분 전, n시간 전 등
                sender.getRole().getLabel()                    // 어머니, 아버지, 아들, 딸 등
        );
    }
}
