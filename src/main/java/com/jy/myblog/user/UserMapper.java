package com.jy.myblog.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    String getHashedUpw(String upw);
}
