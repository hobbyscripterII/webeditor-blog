package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardGetVo.Post> getPost(Pagination.Criteria criteria);
    List<String> getPostPics(int iboard);
    BoardSelVo selPost(int iboard);
    int insNullPost(BoardInsDto dto);
//    int insPost(BoardInsDto dto);
    int updPost(BoardUpdDto dto);
    int insPostPic(BoardInsPicDto dto);
    int delPost(int iboard);
    int delPostPic(String uuidName);
    List<BoardTagGetVo> getTag(String tag);
    int getPostCnt(BoardGetCntDto dto);
}
