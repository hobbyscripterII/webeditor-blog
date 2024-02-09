package com.jy.myblog.board.model;

import lombok.Data;

@Data
public class BoardGetVo {
    private int iboard;
    private int isubject;
    private String nm;
    private String title;
    private String createdAt;
}
