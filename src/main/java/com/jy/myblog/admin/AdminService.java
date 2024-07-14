package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetCategoryVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.common.Const;
import com.jy.myblog.common.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jy.myblog.common.Const.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper mapper;

    public List<AdminGetPostVo> getPostAdmin(Pagination.Criteria criteria) {
        return mapper.getPostAdmin(criteria);
    }

    @Transactional
    public int updPublicFl(AdminUpdDto dto) throws Exception {
        try {
            int rows = mapper.updPublicFl(dto);
            return SUCCESS;
        } catch (Exception e) {
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
    public int updSubjectFl(AdminUpdDto dto) {
        return mapper.updSubjectFl(dto);
    }

    @Transactional
    public int delPostFl(AdminUpdDto dto) throws Exception {
        try {
            mapper.delPostPicFl(dto);
            mapper.delPostFl(dto);
            return SUCCESS;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<AdminGetCategoryVo> getCategory() {
        return mapper.getCategory();
    }

    public int getPostCnt(int isubject) {
        return mapper.getPostCnt(isubject);
    }
}