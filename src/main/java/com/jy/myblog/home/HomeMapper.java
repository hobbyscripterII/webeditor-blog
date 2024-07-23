package com.jy.myblog.home;

import com.jy.myblog.home.model.HomePostGetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeMapper {
    List<HomePostGetVo> getPost();
}
