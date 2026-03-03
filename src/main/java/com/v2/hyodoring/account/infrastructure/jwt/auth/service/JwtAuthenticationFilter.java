package com.v2.hyodoring.account.infrastructure.jwt.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthErrorResponse;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthException;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String REFRESH_TOKEN_HEADER = "Refresh-token";

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 액세스 토큰 추출
        final String accessToken = jwtProvider.resolveToken(request.getHeader(HttpHeaders.AUTHORIZATION));

        // 토큰 값이 없는 경우 다음 필터로 진행
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtProvider.isValidToken(accessToken)) {
                // 액세스 토큰이 유효한 경우 Authentication 저장
                setAuthentication(accessToken);
            } else {
                final String refreshToken = jwtProvider.resolveToken(request.getHeader(REFRESH_TOKEN_HEADER));
                if (StringUtils.hasText(refreshToken) && jwtProvider.isValidToken(refreshToken)) {
                    // 리프레시 토큰이 유효한 경우 액세스 토큰 재발급
                    final String reissuedAccessToken = jwtProvider.reissueAccessToken(refreshToken);
                    response.setHeader(HttpHeaders.AUTHORIZATION, reissuedAccessToken);
                    setAuthentication(reissuedAccessToken);
                } else {
                    // 두 토큰 모두 유효하지 않은 경우 예외 처리
                    throw new AuthException(AuthErrorResponse.INVALID_ACCESS_REFRESH_TOKEN);
                }
            }
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            handleJwtException(response, e);
        }
    }

    private void setAuthentication(String accessToken) {
        // Principal 생성
        final AccountPrincipal principal = jwtProvider.generatePrincipal(accessToken);
        // Authentication 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, accessToken, principal.getAuthorities());
        // SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleJwtException(HttpServletResponse response, AuthException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        CustomResponse.onFailure(e.getErrorResponse(), e.getMessage())
                )
        );
    }
}
