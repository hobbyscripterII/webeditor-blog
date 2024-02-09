package com.jy.myblog.board;

import com.jy.myblog.board.model.BoardGetVo;
import com.jy.myblog.board.model.BoardSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardGetVo> getPost(int isubject);
    BoardSelVo selPost(int iboard);
}
