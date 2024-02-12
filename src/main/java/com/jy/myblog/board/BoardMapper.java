package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardGetVo.Post> getPost(Pagination.Criteria criteria);
    BoardSelVo selPost(int iboard);
    int insPost(BoardInsDto dto);
    int updPost(BoardUpdDto dto);
    int delPost(int iboard);
    List<BoardTagGetVo> getTag(String tag);
    int getPostCnt(int isubject);
}
