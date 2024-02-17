package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public List<BoardGetVo.Post> getPost(Pagination.Criteria criteria) {
        return mapper.getPost(criteria);
    }

    public BoardSelVo selPost(int iboard) {
        return mapper.selPost(iboard);
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
    public int updPost(BoardUpdDto dto) {
        return mapper.updPost(dto);
    }

    @Transactional
    public int delPost(int iboard) {
        return mapper.delPost(iboard);
    }

    public List<BoardTagGetVo> getTag(String tag) {
        return mapper.getTag(tag);
    }

    public int getPostCnt(BoardGetCntDto dto) {
        return mapper.getPostCnt(dto);
    }
}
