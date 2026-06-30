package com.sprint.mission.discodeit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
        );

    SecurityFilterChain securityFilterChain = http.build();

    // 등록된 Spring Security 필터 목록 확인용 로그임
    securityFilterChain.getFilters()
        .forEach(filter -> log.debug("Security filter registered: {}", filter.getClass().getName()));

    return securityFilterChain;
  }
}