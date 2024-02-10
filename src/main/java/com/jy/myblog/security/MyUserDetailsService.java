package com.jy.myblog.security;

import com.jy.myblog.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        MyUserDetails myUserDetails = mapper.getUser(uid);
        log.info("myUserDetails = {}", myUserDetails);
        // 추후 예외처리
        return myUserDetails;
    }
}
