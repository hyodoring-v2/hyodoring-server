package com.v2.hyodoring.family.presentation.greeting.docs;

import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingRequest;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingReplyRequest;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyListResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingRequestListResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Greeting", description = "안부 관련 API")
public interface GreetingApiDocs {

    @Operation(
            summary = "안부 요청하기",
            description = """
                    ### 특정 가족(familyId)의 구성원(receiverId)에게 안부를 요청합니다.
                    content 필드에는 안부 요청 메시지(ex. 아들 뭐해? 밥 먹었어? 등)가 포함됩니다.
                    """
    )
    ResponseEntity<CustomResponse<GreetingRequestResponse>> requestGreeting(AccountPrincipal principal, GreetingRequest greetingRequest);

    @Operation(
            summary = "안부 답장하기",
            description = """
                    ### 특정 가족(familyId)의 구성원(receiverId)이 요청한 안부에 답장합니다.
                    content 필드에는 안부 게시글의 텍스트, imageUrls에는 업로드한 사진의 s3 url이 포함됩니다.
                    """
    )
    ResponseEntity<CustomResponse<GreetingReplyResponse>> replyGreeting(AccountPrincipal principal, GreetingReplyRequest greetingReplyRequest);

    @Operation(
            summary = "안부 요청 목록 조회하기",
            description = "24시간 내에 도착한 안부 요청 중 답장을 보내지 않은 요청 목록을 최신순으로 반환합니다."
    )
    ResponseEntity<CustomResponse<GreetingRequestListResponse>> getGreetingRequestList(AccountPrincipal principal, Long familyId);

    @Operation(
            summary = "안부 답장 목록 조회하기",
            description = "가족이 주고받은 모든 안부를 최신순으로 반환합니다. (페이징 구현 전)"
    )
    ResponseEntity<CustomResponse<GreetingReplyListResponse>> getGreetingReplyList(AccountPrincipal principal, Long familyId);
}
