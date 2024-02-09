package com.jy.myblog.user;

import com.jy.myblog.user.model.UserLoginDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int chkUser(UserLoginDto dto);
}
