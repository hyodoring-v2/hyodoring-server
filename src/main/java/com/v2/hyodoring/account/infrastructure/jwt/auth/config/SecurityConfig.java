package com.v2.hyodoring.account.infrastructure.jwt.auth.config;

import com.v2.hyodoring.account.infrastructure.jwt.auth.service.JwtAuthenticationFilter;
import com.v2.hyodoring.account.infrastructure.jwt.auth.service.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/swagger-ui/**").permitAll()                              // Swagger UI
                .requestMatchers("/v3/**").permitAll()                                      // Swagger API 문서
                .requestMatchers("/swagger-resources/**").permitAll()                       // Swagger 리소스
                .requestMatchers("/api/auth/oauth/**").permitAll()                          // 소셜 로그인
                .requestMatchers("/api/family/code/**").permitAll()
                .anyRequest().authenticated()
        );

        http.csrf(csrf -> csrf.disable())
                .headers(headers -> headers.disable())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
