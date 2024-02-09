package com.jy.myblog.board.model;

import lombok.Data;

@Data
public class BoardSelVo {
    private int iboard;
    private int isubject;
    private String title;
    private String contents;
    private String nm;
    private String createdAt;
}
