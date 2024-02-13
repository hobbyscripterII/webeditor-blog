package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetSubjectVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.common.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminGetPostVo> getPostAdmin(Pagination.Criteria criteria);
    int updPublicFl(AdminUpdDto dto);
    int updSubjectFl(AdminUpdDto dto);
    int delPostFl(AdminUpdDto dto);
    List<AdminGetSubjectVo> getSubject();
    int getPostCnt();
}