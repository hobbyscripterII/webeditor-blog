package com.jy.myblog.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(c -> c.disable())
                .formLogin(f -> f
                        .loginPage("/login") // html 경로
                        .loginProcessingUrl("/login") // url 호출 시 security가 낚아 챔 controller에 구현할 필요 x
                        .usernameParameter("uid")
                        .passwordParameter("upw")
                        .failureUrl("/login?error") // 매핑 url, error param 생략 시 login 화면에 에러 메세지 출력 x
                        .defaultSuccessUrl("/") // 인증 완료 후 호출되는 url
                        .permitAll())
                .exceptionHandling(e -> e
                        .accessDeniedPage("/error/access-denied"))
                .authorizeHttpRequests(a -> a
                        // 권한없이 접근 o
                        .requestMatchers(
                                "/",
                                "/css/**", "/js/**", "/img/**",
                                "/login", "/logout",
                                "/error/**", "/error/access-denied",
                                "/board/list/**", "/board/read/**"
                        ).permitAll()
                        // 외에는 ADMIN만 접근 o
                        .anyRequest().hasRole("ADMIN")
                );
        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test")
                .roles("ADMIN")
                .build();

        // db 사용 x 메모리 사용 o 테스트 환경 / 데모 환경에서 사용됨
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
