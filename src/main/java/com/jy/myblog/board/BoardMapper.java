package com.jy.myblog.board;

import com.jy.myblog.board.model.BoardGetVo;
import com.jy.myblog.board.model.BoardInsDto;
import com.jy.myblog.board.model.BoardSelVo;
import com.jy.myblog.board.model.BoardTagGetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardGetVo.Post> getPost(int isubject);
    BoardSelVo selPost(int iboard);
    int insPost(BoardInsDto dto);
    List<BoardTagGetVo> getTag(String tag);
}
