package com.jy.myblog.board.model;

import lombok.Data;

@Data
public class BoardUpdDto {
    private int iboard;
    private int isubcategory;
    private String title;
    private String contents;
}
