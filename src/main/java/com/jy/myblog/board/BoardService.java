package com.jy.myblog.board;

import com.jy.myblog.board.model.BoardGetVo;
import com.jy.myblog.board.model.BoardSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public List<BoardGetVo> getPost(int isubject) {
        return mapper.getPost(isubject);
    }

    public BoardSelVo selPost(int iboard) {
        return mapper.selPost(iboard);
    }
}
