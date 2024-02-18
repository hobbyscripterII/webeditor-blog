package com.jy.myblog.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardSelVo {
    private int iboard;
    private int isubject;
    private String title;
    private String contents;
    private List<BoardSelVo.File> files;
    private String nm;
    private String createdAt;

    @Data
    public static class File {
        private String originalName;
        private String uuidName;
    }
}
