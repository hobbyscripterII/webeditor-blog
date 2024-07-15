package com.jy.myblog.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardSelVo {
    private int iboard;
    private int icategory;
    private String title;
    private String nm;
    private String contents;
    private String createdAt;
    private List<BoardSelVo.File> files;
    private List<BoardSelVo.Comment> comments;
    private int fileCnt;
    private int commentCnt;

    @Data
    public static class File {
        private int iboardfile;
        private int iboard;
        private String originalName;
        private String uuidName;
    }

    @Data
    public static class Comment {
        private int icomment;
        private String unm;
        private String upw;
        private String contents;
        private String created_at;
    }
}
