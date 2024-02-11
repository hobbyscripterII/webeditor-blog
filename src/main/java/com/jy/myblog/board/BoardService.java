package com.jy.myblog.board;

import com.jy.myblog.board.model.BoardGetVo;
import com.jy.myblog.board.model.BoardInsDto;
import com.jy.myblog.board.model.BoardSelVo;
import com.jy.myblog.board.model.BoardTagGetVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Transient
    public int insPost(BoardInsDto dto) {
        return mapper.insPost(dto);
    }

    public List<BoardTagGetVo> getTag(String tag) {
        return mapper.getTag(tag);
    }
}
