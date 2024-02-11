package com.jy.myblog.board.model;

import lombok.Data;

@Data
public class BoardUpdDto {
    private int isubject;
    private int iboard;
    private String title;
    private String contents;
}
