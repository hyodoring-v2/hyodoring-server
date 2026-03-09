package com.v2.hyodoring.family.core.greeting;

import lombok.Getter;

@Getter
public class GreetingReplyImage {
    private final Long id;
    private final Long greetingId;
    private final String url;

    private GreetingReplyImage(Long id, Long greetingId, String url) {
        this.id = id;
        this.greetingId = greetingId;
        this.url = url;
    }

    public static GreetingReplyImage create(Long greetingId, String url) {
        return new GreetingReplyImage(null, greetingId, url);
    }

    public static GreetingReplyImage of(Long id, Long greetingId, String url) {
        return new GreetingReplyImage(id, greetingId, url);
    }
}
