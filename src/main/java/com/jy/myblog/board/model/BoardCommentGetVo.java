package com.jy.myblog.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardCommentGetVo {
    private int icomment;
    private String unm;
    private String upw;
    private String contents;
    private String created_at;
}
