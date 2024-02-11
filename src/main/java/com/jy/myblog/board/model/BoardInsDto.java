package com.jy.myblog.board.model;

import lombok.Data;

@Data
public class BoardInsDto {
    private int iboard;
    private int isubject;
    private int iuser;
    private String title;
    private String contents;
}
