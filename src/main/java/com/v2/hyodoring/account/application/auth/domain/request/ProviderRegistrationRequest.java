package com.v2.hyodoring.account.application.auth.domain.request;

import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.core.shared.domain.Env;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.util.Assert;

@Schema(description = "인증 제공자 등록 요청 객체")
public record ProviderRegistrationRequest(
        @Schema(description = "개발환경")
        Env env,

        @Schema(description = "인증 제공자")
        Provider provider,

        @Schema(description = "클라이언트 ID")
        String clientId,

        @Schema(description = "클라이언트 비밀키")
        String clientSecret,

        @Schema(description = "리디렉트 uri")
        String redirectUri
) {

    public void validate() {
        Assert.notNull(env, "env must not be null");
        Assert.notNull(provider, "provider must not be null");
        Assert.hasText(clientId, "clientId must not be empty");
        Assert.hasText(clientSecret, "clientSecret must not be empty");
        Assert.hasText(redirectUri, "redirectUri must not be empty");
    }
}
