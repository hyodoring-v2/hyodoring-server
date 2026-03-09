package com.v2.hyodoring.family.presentation.greeting;

import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingRequest;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingReplyRequest;
import com.v2.hyodoring.family.application.greeting.domain.response.GreetingReplyResponse;
import com.v2.hyodoring.family.application.greeting.service.GreetingApiCommandService;
import com.v2.hyodoring.family.application.greeting.service.GreetingApiQueryService;
import com.v2.hyodoring.family.presentation.greeting.docs.GreetingApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/family")
public class GreetingController implements GreetingApiDocs {
    private final GreetingApiCommandService greetingApiCommandService;
    private final GreetingApiQueryService greetingApiQueryService;

    @PostMapping("/greetings/request")
    public ResponseEntity<CustomResponse<Void>> requestGreeting(
            @AuthenticationPrincipal AccountPrincipal principal,
            @RequestBody GreetingRequest greetingRequest
    ) {
        greetingApiCommandService.requestGreeting(principal.getId(), greetingRequest);
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED);
    }

    @PostMapping("/greetings/reply")
    public ResponseEntity<CustomResponse<GreetingReplyResponse>> replyGreeting(
            @AuthenticationPrincipal AccountPrincipal principal,
            @RequestBody GreetingReplyRequest greetingReplyRequest
    ) {
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED,
                greetingApiCommandService.replyGreeting(principal.getId(), greetingReplyRequest));
    }
}
