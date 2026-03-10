package com.v2.hyodoring.family.application.greeting.service;

import com.v2.hyodoring.family.application.greeting.domain.exception.GreetingErrorResponse;
import com.v2.hyodoring.family.application.greeting.domain.exception.GreetingException;
import com.v2.hyodoring.family.core.family.FamilyMember;
import com.v2.hyodoring.family.core.greeting.Greeting;
import com.v2.hyodoring.family.core.greeting.GreetingReply;
import com.v2.hyodoring.family.core.greeting.GreetingReplyImage;
import com.v2.hyodoring.family.core.greeting.GreetingType;
import com.v2.hyodoring.family.core.image.ImageType;
import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.domain.GreetingEntity;
import com.v2.hyodoring.family.infrastructure.jpa.greeting.repository.GreetingQueryRepository;
import com.v2.hyodoring.family.infrastructure.jpa.image.domain.ImageEntity;
import com.v2.hyodoring.family.infrastructure.jpa.image.repository.ImageQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GreetingQueryService {
    private final GreetingQueryRepository greetingQueryRepository;
    private final ImageQueryRepository imageQueryRepository;

    /**
     * 수신한 안부 요청 목록을 조회하는 메서드
     * @param familyId 가족 id
     * @param receiver 안부 요청 수신자
     * @return 수신자가 24시간 내에 받은 안부 요청 중 아직 답장하지 않은 요청 목록을 최신순으로 반환
     */
    public List<Greeting> getReceivedGreetingRequestList(Long familyId, FamilyMember receiver) {
        // 24시간 내에 수신한 모든 안부 요청 조회
        return greetingQueryRepository.findAllByFamilyIdAndReceiverIdAndTypeAndCreatedAtAfter(
                familyId, receiver.getAccountId(), GreetingType.REQUEST, LocalDateTime.now().minusDays(1)).stream()
                // 이미 답장한 안부 요청은 목록에서 제외
                .filter(greetingEntity -> !greetingEntity.isChecked())
                // 최신순으로 정렬
                .sorted(Comparator.comparing(BaseEntity::getCreatedAt).reversed())
                // 리턴 형식으로 변환
                .map(GreetingEntity::toGreeting)
                .toList();
    }


    /**
     * 가족이 주고받은 모든 안부를 조회하는 메서드
     * @param familyId 가족 id
     * @return 가족이 주고받은 안부 목록을 최신순으로 반환
     */
    public List<GreetingReply> getAllGreetingReplyList(Long familyId) {
        //TODO: 페이지네이션 추가
        return greetingQueryRepository.findAllByFamilyIdAndType(familyId, GreetingType.REPLY).stream()
                // 최신순으로 정렬
                .sorted(Comparator.comparing(BaseEntity::getCreatedAt).reversed())
                // 리턴 형식으로 변환
                .map(GreetingEntity::toGreetingReply)
                .toList();
    }

    /**
     * 안부에 포함된 모든 이미지를 조회하는 메서드
     * @param replyId 안부 id
     * @return 안부에 포함된 이미지 목록
     */
    public List<GreetingReplyImage> getGreetingReplyImageList(Long replyId) {
        // 안부가 존재하는지 확인
        greetingQueryRepository.findById(replyId)
                .orElseThrow(() -> new GreetingException(GreetingErrorResponse.GREETING_REPLY_NOT_FOUND));
        // 안부에 포함된 이미지 목록 반환
        return imageQueryRepository.findAllByTargetIdAndTargetType(replyId, ImageType.GREETING_IMAGE).stream()
                .map(ImageEntity::toGreetingReplyImage)
                .toList();
    }
}
