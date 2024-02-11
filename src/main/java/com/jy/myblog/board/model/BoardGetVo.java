package com.jy.myblog.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardGetVo {
    private int isubject;
    private String title;
    private List<BoardGetVo.Post> posts;

    @Data
    public static class Post {
    private int iboard;
    private String nm;
    private String title;
    private String createdAt;
    }
}
