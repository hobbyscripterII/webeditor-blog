package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetSubjectVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminGetPostVo> getPostAdmin();
    int updPublicFl(AdminUpdDto dto);
    int updSubjectFl(AdminUpdDto dto);
    List<AdminGetSubjectVo> getSubject();
}