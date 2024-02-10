package com.jy.myblog.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    // >>>>> spring security 적용 전 방법 - 사용 x >>>>>
//    public boolean login(UserLoginDto dto) {
//        String hashedUpw = mapper.getHashedUpw(dto.getUpw());
//        log.info("hashedUpw = {}", hashedUpw);
//        if (BCrypt.checkpw(dto.getUpw(), hashedUpw)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
