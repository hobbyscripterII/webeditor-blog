package com.jy.myblog.board.model;

import lombok.Getter;

@Getter
public class BoardUpdDto {
    private int iboard;
    private int isubcategory;
    private String title;
    private String contents;
}
