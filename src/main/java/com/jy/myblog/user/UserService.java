package com.jy.myblog.user;

import com.jy.myblog.user.model.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    // spring security 적용 전
//    public boolean login(UserLoginDto dto) {
//        String hashedUpw = mapper.getHashedUpw(dto.getUpw());
//        log.info("hashedUpw = {}", hashedUpw);
//        if(BCrypt.checkpw(dto.getUpw(), hashedUpw)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
