package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminPostGetVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.board.model.BoardUpdDto;
import com.jy.myblog.common.Const;
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

    public List<AdminPostGetVo> getPostAdmin() {
        return mapper.getPostAdmin();
    }

    @Transactional
    public int updPrivate(AdminUpdDto dto) throws Exception {
        try {
        int rows = mapper.updPrivate(dto);
            return Const.SUCCESS;
        } catch(Exception e) {
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
}