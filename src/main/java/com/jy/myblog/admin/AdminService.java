package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetSubjectVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.common.Const;
import com.jy.myblog.common.PageNation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper mapper;

    public List<AdminGetPostVo> getPostAdmin(PageNation.Criteria criteria) {
        return mapper.getPostAdmin(criteria);
    }

    @Transactional
    public int updPublicFl(AdminUpdDto dto) throws Exception {
        try {
            int rows = mapper.updPublicFl(dto);
            return Const.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Const.FAIL;
        }

//        try {
//            if(rows == list.size()) {
//                return Const.SUCCESS;
//            } else {
//                throw new Exception();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception();
//        }
    }

    @Transactional
    public int updSubjectFl(AdminUpdDto dto) throws Exception {
        try {
            int rows = mapper.updSubjectFl(dto);
            return Const.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Const.FAIL;
        }
    }

    public List<AdminGetSubjectVo> getSubject() {
        return mapper.getSubject();
    }

    public int getPostCnt() {
        return mapper.getPostCnt();
    }
}