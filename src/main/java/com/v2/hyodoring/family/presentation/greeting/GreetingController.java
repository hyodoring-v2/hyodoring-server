package com.v2.hyodoring.family.presentation.greeting;

import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingRequest;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingReplyRequest;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyListResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingRequestListResponse;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingRequestResponse;
import com.v2.hyodoring.family.application.greeting.service.GreetingApiCommandService;
import com.v2.hyodoring.family.application.greeting.service.GreetingApiQueryService;
import com.v2.hyodoring.family.presentation.greeting.docs.GreetingApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/family")
public class GreetingController implements GreetingApiDocs {
    private final GreetingApiCommandService greetingApiCommandService;
    private final GreetingApiQueryService greetingApiQueryService;

    @PostMapping("/greetings/request")
    public ResponseEntity<CustomResponse<GreetingRequestResponse>> requestGreeting(
            @AuthenticationPrincipal AccountPrincipal principal,
            @RequestBody GreetingRequest greetingRequest
    ) {
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED,
                greetingApiCommandService.requestGreeting(principal.getId(), greetingRequest));
    }

    @PostMapping("/greetings/reply")
    public ResponseEntity<CustomResponse<GreetingReplyResponse>> replyGreeting(
            @AuthenticationPrincipal AccountPrincipal principal,
            @RequestBody GreetingReplyRequest greetingReplyRequest
    ) {
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED,
                greetingApiCommandService.replyGreeting(principal.getId(), greetingReplyRequest));
    }

    @GetMapping("/{familyId}/greetings/request")
    public ResponseEntity<CustomResponse<GreetingRequestListResponse>> getGreetingRequestList(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable Long familyId
    ) {
        return CustomResponse.onSuccess(BaseSuccessResponse.OK,
                greetingApiQueryService.getGreetingRequestList(familyId, principal.getId()));
    }

    @GetMapping("/{familyId}/greetings/reply")
    public ResponseEntity<CustomResponse<GreetingReplyListResponse>> getGreetingReplyList(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable Long familyId
    ) {
        return CustomResponse.onSuccess(BaseSuccessResponse.OK,
                greetingApiQueryService.getGreetingReplyList(familyId, principal.getId()));
    }
}
