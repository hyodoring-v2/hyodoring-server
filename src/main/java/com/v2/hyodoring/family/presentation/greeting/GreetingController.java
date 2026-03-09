package com.v2.hyodoring.family.presentation.greeting;

import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingRequest;
import com.v2.hyodoring.family.application.greeting.domain.request.GreetingReplyRequest;
import com.v2.hyodoring.family.application.greeting.service.GreetingApiCommandService;
import com.v2.hyodoring.family.application.greeting.service.GreetingApiQueryService;
import com.v2.hyodoring.family.presentation.greeting.docs.GreetingApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/family")
public class GreetingController implements GreetingApiDocs {
    private final GreetingApiCommandService greetingApiCommandService;
    private final GreetingApiQueryService greetingApiQueryService;

    @PostMapping("/greetings/request")
    public ResponseEntity<CustomResponse<Void>> requestGreeting(GreetingRequest greetingRequest) {
        greetingApiCommandService.requestGreeting(greetingRequest);
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED);
    }

    @PostMapping("/greetings/reply")
    public ResponseEntity<CustomResponse<Void>> replyGreeting(GreetingReplyRequest greetingReplyRequest) {
        greetingApiCommandService.replyGreeting(greetingReplyRequest);
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED);
    }
}
