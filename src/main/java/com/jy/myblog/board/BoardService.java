package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.Const;
import com.jy.myblog.common.Pagination;
import com.jy.myblog.common.UploadUtil;
import com.jy.myblog.common.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jy.myblog.common.Const.FAIL;
import static com.jy.myblog.common.Const.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public List<BoardGetVo.Post> getPost(Pagination.Criteria criteria) {
        return mapper.getPost(criteria);
    }

    public List<String> getPostPics(int iboard) {
        return mapper.getPostPics(iboard);
    }

//    public List<BoardSelVo.File> getPostFile(int iboard) {
//        return mapper.getPostFile(iboard);
//    }

    public BoardSelVo selPost(int iboard) {
        BoardSelVo vo = mapper.selPost(iboard);

        if (vo.getFileCnt() > 0) { vo.setFiles(mapper.getPostFile(iboard)); }
        else if(vo.getCommentCnt() > 0) { vo.setComments(mapper.getComment(iboard)); }

        return vo;
    }

    public BoardSelVo.File selPostFile(int iboardfile) {
        return mapper.selPostFile(iboardfile);
    }

    @Transactional
    public int insNullPost(BoardInsDto dto) {
        return mapper.insNullPost(dto);
    }

    @Transactional
    public int insPostPic(BoardInsPicDto dto) {
        return mapper.insPostPic(dto);
    }

    @Transactional
    public int insPostFile(BoardInsFileDto dto) {
        return mapper.insPostFile(dto);
    }

    @Transactional
    public int updPost(BoardUpdDto dto) throws Exception {
        try {
            if (Util.isNotNull(mapper.updPost(dto))) { return SUCCESS; }
            else { throw new Exception(); }
        } catch (Exception e) {
            return FAIL;
        }
    }

    @Transactional
    public int delPost(int iboard) throws Exception {
        try {
            mapper.delPost(iboard);
            mapper.delPostPics(iboard);
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }

    @Transactional
    public int delPostPic(String uuidName) {
        return mapper.delPostPic(uuidName);
    }

    public int getPostCnt(BoardGetCntDto dto) {
        return mapper.getPostCnt(dto);
    }

    public int insComment(BoardCommentInsDto dto) {
        try {
            dto.setUpw(BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt()));

            if(Util.isNotNull(mapper.insComment(dto))) {
                return SUCCESS;
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

//        public List<BoardTagGetVo> getTag(String tag) {
//        return mapper.getTag(tag);
//    }
}
