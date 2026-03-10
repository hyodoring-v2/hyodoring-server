package com.v2.hyodoring.family.application.greeting.service;

import com.v2.hyodoring.family.application.greeting.domain.exception.GreetingErrorResponse;
import com.v2.hyodoring.family.application.greeting.domain.exception.GreetingException;
import com.v2.hyodoring.family.core.greeting.Greeting;
import com.v2.hyodoring.family.core.greeting.GreetingReply;
import com.v2.hyodoring.family.core.greeting.GreetingType;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.domain.GreetingEntity;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.repository.GreetingCommandRepository;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.repository.GreetingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GreetingCommandService {

    private final GreetingCommandRepository greetingCommandRepository;
    private final GreetingQueryRepository greetingQueryRepository;

    public Greeting saveGreeting(Greeting greeting) {
        return greetingCommandRepository.save(GreetingEntity.from(greeting)).toGreeting();
    }

    public GreetingReply saveGreetingReply(GreetingReply greetingReply) {
        return greetingCommandRepository.save(GreetingEntity.from(greetingReply)).toGreetingReply();
    }

    public void checkGreeting(Long greetingId) {
        // 안부 요청 조회
        final GreetingEntity greetingEntity = greetingQueryRepository.findByIdAndType(greetingId, GreetingType.REQUEST)
                .orElseThrow(() -> new GreetingException(GreetingErrorResponse.GREETING_REQUEST_NOT_FOUND));
        // 조회 상태 업데이트
        greetingEntity.updateCheckStatus();
        greetingCommandRepository.save(greetingEntity);
    }
}
