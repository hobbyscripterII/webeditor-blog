package com.jy.myblog.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
        return httpSecurity.build();
    }

    // >>>>> db 연결 전 사용 - 사용 x >>>>>
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() { // InMemoryUserDetailsManager - 메모리 저장 o, 테스트 / 데모 환경에서 사용됨
//        List<UserDetails> users = new ArrayList();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("1234")
//                .roles("USER")
//                .build();
//        users.add(user);
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("1234")
//                .roles("ADMIN")
//                .build();
//        users.add(admin);
//
//        return new InMemoryUserDetailsManager(users);
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}