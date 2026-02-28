package com.v2.hyodoring.account.core.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    @Value("${springdoc.dev}")
    private String devServerUrl;

    @Bean
    public OpenAPI openAPI() {

        // 로컬 서버
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("LOCAL");

        // 개발 서버
        Server devServer = new Server()
                .url(devServerUrl)
                .description("PROD");

        Info info = new Info()
                .title("Hyodoring API")
                .description("Hyodoring API 명세서")
                .version("1.0.0");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, devServer));
    }
}
