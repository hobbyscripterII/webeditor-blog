package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardGetVo.Post> getPost(Pagination.Criteria criteria);
    List<String> getPostPics(int iboard);
    List<BoardSelVo.File> getPostFile(int iboard);
    BoardSelVo selPost(int iboard);
    BoardSelVo.File selPostFile(int iboardfile);
    int insNullPost(BoardInsDto dto);
//    int insPost(BoardInsDto dto);
    int updPost(BoardUpdDto dto);
    int insPostPic(BoardInsPicDto dto);
    int insPostFile(BoardInsFileDto dto);
    int delPost(int iboard);
    int delPostPic(String uuidName);
    int delPostPics(int iboard);
    List<BoardTagGetVo> getTag(String tag);
    int getPostCnt(BoardGetCntDto dto);
}
