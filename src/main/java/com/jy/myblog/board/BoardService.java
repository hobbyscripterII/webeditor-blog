package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.Const;
import com.jy.myblog.common.Pagination;
import com.jy.myblog.common.UploadUtil;
import com.jy.myblog.common.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<BoardSelVo.File> getPostFile(int iboard) {
        return mapper.getPostFile(iboard);
    }

    public BoardSelVo selPost(int iboard) {
        BoardSelVo vo = mapper.selPost(iboard);
        List<BoardSelVo.File> files = mapper.getPostFile(iboard);

        if (files.size() > 0) {
            vo.setFiles(files);
            log.info("files = {}", files);
            log.info("vo.getFiles = {}", vo.getFiles());
        }

        return vo;
    }

    @Transactional
    public int insNullPost(BoardInsDto dto) {
        return mapper.insNullPost(dto);
    }

//    @Transactional
//    public int insPost(BoardInsDto dto) {
//        return mapper.insPost(dto);
//    }

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
            int rows = mapper.updPost(dto);

            if (Util.isNotNull(rows)) {
                return SUCCESS;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Transactional
    public int delPost(int iboard) throws Exception {
        try {
            mapper.delPost(iboard);
            mapper.delPostPics(iboard);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Transactional
    public int delPostPic(String uuidName) {
        return mapper.delPostPic(uuidName);
    }

    public List<BoardTagGetVo> getTag(String tag) {
        return mapper.getTag(tag);
    }

    public int getPostCnt(BoardGetCntDto dto) {
        return mapper.getPostCnt(dto);
    }
}
