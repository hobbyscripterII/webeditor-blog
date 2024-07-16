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

import java.sql.SQLException;
import java.util.List;

import static com.jy.myblog.common.Const.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public List<BoardGetVo.Post> getPost(Pagination.Criteria criteria) {
        return mapper.getPost(criteria);
    }

    public BoardSelVo selPost(int iboard) {
        BoardSelVo vo = mapper.selPost(iboard);

        if (vo.getFileCnt() > 0) { vo.setFiles(mapper.getPostFile(iboard)); }
        else if(vo.getCommentCnt() > 0) { vo.setComments(mapper.getComment(iboard)); }

        return vo;
    }

    @Transactional
    public int insNullPost(BoardInsDto dto) {
        return mapper.insNullPost(dto);
    }

    @Transactional
    public int insPostPic(BoardInsPicDto dto) {
        return mapper.insPostPic(dto);
    }

    public BoardSelVo.File selPostFile(int iboardfile) {
        return mapper.selPostFile(iboardfile);
    }

    @Transactional
    public int insPostFile(BoardInsFileDto dto) {
        return mapper.insPostFile(dto);
    }

    @Transactional
    public int updPost(BoardUpdDto dto) {
        try {
            if (Util.isNotNull(mapper.updPost(dto))) { return SUCCESS; }
            else { throw new Exception(); }
        } catch (Exception e) {
            return FAIL;
        }
    }

    @Transactional
    public int delPost(int iboard) {
        try {
            mapper.delPost(iboard);
            mapper.delPostPics(iboard);
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }

    public List<String> getPostPics(int iboard) {
        return mapper.getPostPics(iboard);
    }

    @Transactional
    public int delPostPic(String uuidName) {
        return mapper.delPostPic(uuidName);
    }

    // 중복 메소드 제거
    private boolean passwordCheck(int icomment, String upw) {
        // mapper.getCommentPassword(icomment) - DB에 저장되어 있던 댓글 패스워드 가져옴
        // BCrypt.checkpw - Security 내장 BCrypt로 패스워드 확인
        return BCrypt.checkpw(upw, mapper.getCommentPassword(icomment));
    }

    public int delComment(BoardCommentDelDto dto) {
        try {
            if (passwordCheck(dto.getIcomment(), dto.getUpw())) {
                if (Util.isNotNull(mapper.delComment(dto.getIcomment()))) { return SUCCESS; }
                else { throw new SQLException(); }
            } else {
                return PASSWORD_MISMATCH_ERROR;
            }
        } catch (SQLException e) {
            return FAIL;
        }
    }

    public int updComment(BoardCommentUpdDto dto) {
        try {
            if (passwordCheck(dto.getIcomment(), dto.getUpw())) {
                if (Util.isNotNull(mapper.updComment(dto))) { return SUCCESS; }
                else { throw new SQLException(); }
            } else {
                return PASSWORD_MISMATCH_ERROR;
            }
        } catch (SQLException e) {
            return FAIL;
        }
    }

    public int insComment(BoardCommentInsDto dto) {
        try {
            dto.setUpw(BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt()));

            if(Util.isNotNull(mapper.insComment(dto))) { return SUCCESS; }
            else { return FAIL; }
        } catch(Exception e) {
            return FAIL;
        }
    }

    public int getPostCnt(BoardGetCntDto dto) {
        return mapper.getPostCnt(dto);
    }

//        public List<BoardTagGetVo> getTag(String tag) {
//        return mapper.getTag(tag);
//    }
}
