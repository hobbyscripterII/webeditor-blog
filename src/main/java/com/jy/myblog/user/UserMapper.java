package com.jy.myblog.user;

import com.jy.myblog.security.MyUserDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    MyUserDetails getUser(String uid);
}
