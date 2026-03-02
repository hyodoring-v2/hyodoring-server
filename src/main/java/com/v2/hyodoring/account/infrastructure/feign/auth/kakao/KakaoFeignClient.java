package com.v2.hyodoring.account.infrastructure.feign.auth.kakao;

import com.v2.hyodoring.account.core.config.FeignConfig;
import com.v2.hyodoring.account.infrastructure.feign.auth.OIDCPublicKeyResponse;
import com.v2.hyodoring.account.infrastructure.feign.auth.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoFeignClient",
        url = "${feign.client.kakao.url}",
        configuration = FeignConfig.class
)
public interface KakaoFeignClient {

    /**
     * 카카오 토큰 발급 API (미사용)
     * OpenID Connect 방식으로 클라이언트가 권한 부여 코드를 통해 액세스 토큰을 요청하는 메서드
     */
    @PostMapping("/oauth/token")
    TokenResponse getToken(@RequestParam(value = "grant_type", defaultValue = "authorization_code") String grantType,
                           @RequestParam("client_id") String clientId,
                           @RequestParam("redirect_uri") String redirectUri,
                           @RequestParam("code") String code,
                           @RequestParam("client_secret") String clientSecret);

    /**
     * 카카오 공개 키 조회 API
     * JWT 파싱을 위한 OIDC 공개 키를 조회하는 메서드
     */
    @GetMapping("/.well-known/jwks.json")
    OIDCPublicKeyResponse getPublicKeys();

}
