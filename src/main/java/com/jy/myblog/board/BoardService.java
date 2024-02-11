package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public List<BoardGetVo.Post> getPost(int isubject) {
        return mapper.getPost(isubject);
    }

    public BoardSelVo selPost(int iboard) {
        return mapper.selPost(iboard);
    }

    @Transactional
    public int insPost(BoardInsDto dto) {
        return mapper.insPost(dto);
    }

    @Transactional
    public int updPost(BoardUpdDto dto) {
        return mapper.updPost(dto);
    }

    public int delPost(int iboard) {
        return mapper.delPost(iboard);
    }

    public List<BoardTagGetVo> getTag(String tag) {
        return mapper.getTag(tag);
    }
}
