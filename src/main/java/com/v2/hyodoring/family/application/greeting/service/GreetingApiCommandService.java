package com.v2.hyodoring.family.application.greeting.service;


import com.v2.hyodoring.family.application.greeting.domain.request.GreetingRequest;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingReplyRequest;
import com.v2.hyodoring.family.core.greeting.Greeting;
import com.v2.hyodoring.family.core.greeting.GreetingReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GreetingApiCommandService {

    private final GreetingCommandService greetingCommandService;

    public void requestGreeting(GreetingRequest greetingRequest) {
        // 안부를 생성하여 DB에 저장
        final Greeting greeting = greetingCommandService.saveGreeting(
                Greeting.create(
                        greetingRequest.getFamilyId(),
                        greetingRequest.getSenderId(),
                        greetingRequest.getReceiverId(),
                        greetingRequest.getContent()
                )
        );
        //TODO: 푸시알림 전송
    }

    public void replyGreeting(GreetingReplyRequest greetingReplyRequest) {
        final GreetingReply greetingReply = greetingCommandService.saveGreetingReply(
                GreetingReply.create(
                        greetingReplyRequest.getFamilyId(),
                        greetingReplyRequest.getSenderId(),
                        greetingReplyRequest.getReceiverId(),
                        greetingReplyRequest.getContent()
                )
        );
        //TODO: 이미지 저장
    }
}
