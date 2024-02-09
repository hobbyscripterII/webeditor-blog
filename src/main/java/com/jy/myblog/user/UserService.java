package com.jy.myblog.user;

import com.jy.myblog.user.model.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    int chkUser(UserLoginDto dto) {
        return mapper.chkUser(dto);
    }
}
