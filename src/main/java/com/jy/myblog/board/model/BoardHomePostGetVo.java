package com.jy.myblog.board.model;

import lombok.Data;

@Data
public class BoardHomePostGetVo {
    private int iboard;
    private int icategory;
    private String pics;
    private String title;
    private String contents;
    private String createdAt;
}
