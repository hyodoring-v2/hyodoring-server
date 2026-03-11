package com.v2.hyodoring.account.presentation.auth.docs;

import com.v2.hyodoring.account.application.auth.domain.request.ProviderRegistrationRequest;
import com.v2.hyodoring.account.application.auth.domain.response.AuthProviderResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth (Internal)", description = "인증 관련 내부 API")
public interface InternalAuthApiDocs {

    @Operation(
            summary = "인증 제공자 등록",
            description = """
                    ### 서비스에서 사용 가능한 인증 제공자 정보(client id, client secret, redirect uri)를 등록합니다.
                    (프론트 호출 X)
                    """
    )
    ResponseEntity<CustomResponse<AuthProviderResponse>> registerAuthProvider(ProviderRegistrationRequest providerRegistrationRequest);
}
