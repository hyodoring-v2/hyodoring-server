package com.v2.hyodoring.family.application.greeting.service;


import com.v2.hyodoring.family.application.family.service.FamilyQueryService;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingRequest;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingReplyRequest;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyResponse;
import com.v2.hyodoring.family.application.image.service.ImageCommandService;
import com.v2.hyodoring.family.core.family.FamilyMember;
import com.v2.hyodoring.family.core.greeting.Greeting;
import com.v2.hyodoring.family.core.greeting.GreetingReply;
import com.v2.hyodoring.family.core.greeting.GreetingReplyImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GreetingApiCommandService {

    private final FamilyQueryService familyQueryService;
    private final GreetingCommandService greetingCommandService;
    private final ImageCommandService imageCommandService;

    public void requestGreeting(Long senderId, GreetingRequest greetingRequest) {
        // 안부를 생성하여 DB에 저장
        final Greeting greeting = greetingCommandService.saveGreeting(
                Greeting.create(
                        greetingRequest.getFamilyId(),
                        senderId,
                        greetingRequest.getReceiverId(),
                        greetingRequest.getContent()
                )
        );
        //TODO: 푸시알림 전송
    }

    public GreetingReplyResponse replyGreeting(Long senderId, GreetingReplyRequest request) {
        // 안부 답장 생성
        final GreetingReply greetingReply = greetingCommandService.saveGreetingReply(
                GreetingReply.create(
                        request.getFamilyId(),
                        senderId,
                        request.getReceiverId(),
                        request.getContent()
                )
        );

        // 이미지 저장
        final List<GreetingReplyImage> images = imageCommandService
                .uploadGreetingImages(greetingReply.getId(), request.getImageUrls());

        // 안부 송수신자 정보 조회
        final FamilyMember sender = familyQueryService
                .getFamilyMember(request.getFamilyId(), request.getSenderId());
        final FamilyMember receiver = familyQueryService
                .getFamilyMember(request.getFamilyId(), request.getSenderId());

        return GreetingReplyResponse.of(greetingReply, sender, receiver, images);
    }
}
