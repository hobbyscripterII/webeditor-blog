package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminPostGetVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminPostGetVo> getPostAdmin();
    int updPrivate(AdminUpdDto dto);
}