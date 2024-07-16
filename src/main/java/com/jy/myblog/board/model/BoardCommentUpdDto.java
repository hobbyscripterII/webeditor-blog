package com.jy.myblog.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardCommentUpdDto {
    private int icomment;
    private String contents;
    private String upw;
}