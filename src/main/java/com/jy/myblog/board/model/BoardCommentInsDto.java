package com.jy.myblog.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardCommentInsDto {
    private int iboard;
    private String unm;
    private String upw;
    private String contents;
}
