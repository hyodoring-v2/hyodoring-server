package com.v2.hyodoring.family.application.greeting.service;

import com.v2.hyodoring.family.application.greeting.domain.request.GreetingRequest;
import com.v2.hyodoring.family.core.greeting.Greeting;
import com.v2.hyodoring.family.core.greeting.GreetingReply;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.domain.GreetingEntity;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.repository.GreetingCommandRepository;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.repository.GreetingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreetingCommandService {

    private final GreetingCommandRepository greetingCommandRepository;
    private final GreetingQueryRepository greetingQueryRepository;

    public Greeting saveGreeting(Greeting greeting) {
        return greetingCommandRepository.save(GreetingEntity.from(greeting)).toGreeting();
    }

    public GreetingReply saveGreetingReply(GreetingReply greetingReply) {
        return greetingCommandRepository.save(GreetingEntity.from(greetingReply)).toGreetingReply();
    }
}
