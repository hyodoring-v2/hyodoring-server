package com.v2.hyodoring.family.presentation.notification.docs;

import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Notification", description = "알림 설정 관련 API")
public interface NotificationApiDocs {

    @Operation(
            summary = "FCM 토큰 저장",
            description = """
                    ### 클라이언트가 발급한 FCM 토큰을 DB에 저장합니다.
                    return value : 저장한 토큰 string
                    """
    )
    ResponseEntity<CustomResponse<String>> saveFCMToken(AccountPrincipal principal, String token);
}
