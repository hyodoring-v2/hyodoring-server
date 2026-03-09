package com.v2.hyodoring.family.presentation.notification;

import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import com.v2.hyodoring.family.application.notification.service.NotificationApiCommandService;
import com.v2.hyodoring.family.presentation.notification.docs.NotificationApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController implements NotificationApiDocs {
    private final NotificationApiCommandService notificationApiCommandService;

    @PostMapping("/fcm/token")
    public ResponseEntity<CustomResponse<String>> saveFCMToken(
            @AuthenticationPrincipal AccountPrincipal principal,
            @RequestParam String token
    ) {
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED,
                notificationApiCommandService.saveFCMToken(principal.getId(), token).getToken());
    }
}
