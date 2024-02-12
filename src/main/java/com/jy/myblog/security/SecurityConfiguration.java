package com.jy.myblog.security;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(c -> c.disable())
                .formLogin(in -> in
                        .loginPage("/login") // html 경로
                        .loginProcessingUrl("/login") // url 호출 시 security가 낚아 챔 controller에 구현할 필요 x
                        .usernameParameter("uid")
                        .passwordParameter("upw")
                        .failureUrl("/login?error") // 매핑 url, error param 생략 시 login 화면에 에러 메세지 출력 x
                        .defaultSuccessUrl("/") // 인증 완료 후 호출되는 url
                        .permitAll())
                .exceptionHandling(e -> e
                        .accessDeniedPage("/access-denied?error")) // 매핑 url
                .authorizeHttpRequests(a -> a
                        // 권한없이 접근 o
                        .requestMatchers(
                                "/", "/css/**", "/js/**", "/img/**", "/error/**",
                                "/login", "/logout", "/access-denied", "/board/list/**", "/board/read/**"
                        ).permitAll()
                        // 외에는 ADMIN만 접근 o
                        .anyRequest().hasRole("ADMIN")
                );
        httpSecurity.logout(out -> {
                 out.logoutUrl("/logout")
                    .addLogoutHandler((request, response, authentication) -> {
                        HttpSession session = request.getSession();
                        if (session != null) {
                            session.invalidate();
                        }
                    })
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.sendRedirect("/");
                    });
//                    .deleteCookies("");
        });
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}