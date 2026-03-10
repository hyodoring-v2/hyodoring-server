package com.v2.hyodoring.family.application.greeting.service;

import com.v2.hyodoring.family.application.family.service.FamilyQueryService;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyListResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingRequestListResponse;
import com.v2.hyodoring.family.core.family.FamilyMember;
import com.v2.hyodoring.family.core.greeting.GreetingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GreetingApiQueryService {
    private final FamilyQueryService familyQueryService;
    private final GreetingQueryService greetingQueryService;

    /**
     * 수신자에게 도착한 가족 안부 요청 목록 조회 메서드
     * @param familyId 가족 id
     * @param accountId 수신자 id (나)
     * @return 수신자에게 도착한 가족 안부 요청 목록 반환
     */
    public GreetingRequestListResponse getGreetingRequestList(Long familyId, Long accountId) {
        // 가족이 존재하는지 확인
        familyQueryService.getFamilyInfo(familyId);

        // 안부 요청 수신자 정보 조회
        final FamilyMember sender = familyQueryService.getFamilyMember(familyId, accountId);

        // 수신자에게 도착한 안부 요청 목록 최신순 조회
        return new GreetingRequestListResponse(familyId, greetingQueryService
                .getReceivedGreetingRequestList(familyId, sender).stream()
                .map(greeting -> new GreetingMessage(
                        greeting.getContent(),
                        GreetingMessage.generateBody(greeting, sender)
                ))
                .toList());
    }

    /**
     * 가족이 주고받은 안부 목록 조회 메서드
     * @param familyId 가족 id
     * @return 가족이 주고받은 안부 목록을 최신순으로 반환
     */
    public GreetingReplyListResponse getGreetingReplyList(Long familyId, Long accountId) {

        // 가족이 존재하는지 확인
        familyQueryService.getFamilyInfo(familyId);

        // 안부를 열람한 구성원이 존재하는지 확인
        familyQueryService.getFamilyMember(familyId, accountId);

        // 가족이 주고받은 모든 안부 조회
        List<GreetingReplyResponse> greetingReplyResponseList = greetingQueryService.getAllGreetingReplyList(familyId).stream()
                .map(reply -> {
                    // 안부 송수신자 정보 조회
                    final FamilyMember sender = familyQueryService
                            .getFamilyMember(familyId, reply.getSenderId());
                    final FamilyMember receiver = familyQueryService
                            .getFamilyMember(familyId, reply.getReceiverId());

                    // 안부 목록 생성
                    return GreetingReplyResponse.of(reply, sender, receiver,
                            greetingQueryService.getGreetingReplyImageList(reply.getId()));
                })
                .toList();

        return new GreetingReplyListResponse(familyId, greetingReplyResponseList);
    }
}
