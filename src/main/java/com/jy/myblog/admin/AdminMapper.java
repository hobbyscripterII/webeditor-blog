package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetSubjectVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.common.PageNation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminGetPostVo> getPostAdmin(PageNation.Criteria criteria);
    int updPublicFl(AdminUpdDto dto);
    int updSubjectFl(AdminUpdDto dto);
    List<AdminGetSubjectVo> getSubject();
    int getPostCnt();
}