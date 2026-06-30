package com.sprint.mission.discodeit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // CSR/SPA 환경에서 사용할 CSRF 설정임
        // XSRF-TOKEN 쿠키를 발급하고, 클라이언트는 X-XSRF-TOKEN 헤더로 다시 전송함
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
        )

        // 요청별 인가 규칙 설정함
        // 인증 없이 접근 가능한 API와 리소스를 먼저 열고, 나머지는 인증 요구함
        .authorizeHttpRequests(auth -> auth
            // CSRF 토큰 발급 API는 로그인 전에도 호출되어야 하므로 허용함
            .requestMatchers(HttpMethod.GET, "/api/auth/csrf-token").permitAll()

            // 회원가입 API는 로그인 전 호출되어야 하므로 허용함
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

            // Spring Security formLogin이 처리할 로그인 URL임
            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

            // 로그아웃 요청은 Security logout filter가 처리해야 하므로 허용함
            .requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()

            // 정적 프론트엔드 리소스 접근 허용함
            .requestMatchers(
                "/",
                "/index.html",
                "/favicon.ico",
                "/assets/**",
                "/*.js",
                "/*.css",
                "/user-list.html"
            ).permitAll()

            // Swagger/OpenAPI 문서 접근 허용함
            .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html"
            ).permitAll()

            // Actuator는 상태 확인 용도로 열어둠
            // 추후 운영 환경에서는 필요한 endpoint만 제한하는 게 좋음
            .requestMatchers("/actuator/**").permitAll()

            // 위에서 허용하지 않은 모든 요청은 인증 필요함
            .anyRequest().authenticated()
        );

    SecurityFilterChain securityFilterChain = http.build();

    // 등록된 Spring Security 필터 목록 확인용 로그임
    securityFilterChain.getFilters()
        .forEach(filter -> log.debug("Security filter registered: {}", filter.getClass().getName()));

    return securityFilterChain;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // BCrypt 기반 비밀번호 해시 인코더 등록함
    // 회원가입, 비밀번호 변경, 로그인 검증에서 같은 방식으로 사용함
    return new BCryptPasswordEncoder();
  }
}