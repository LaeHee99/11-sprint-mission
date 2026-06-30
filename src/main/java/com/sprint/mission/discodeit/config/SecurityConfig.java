package com.sprint.mission.discodeit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 가장 기본적인 SecurityFilterChain 등록함
    // 이후 CSRF, formLogin, logout, authorizeHttpRequests 설정을 단계적으로 추가할 예정임
    SecurityFilterChain securityFilterChain = http.build();

    // 필터 목록 확인용 로그임
    // 요구사항에서 필터 목록을 디버깅해보라고 했기 때문에 남겨둠
    securityFilterChain.getFilters()
        .forEach(filter -> log.debug("Security filter registered: {}", filter.getClass().getName()));

    return securityFilterChain;
  }
}