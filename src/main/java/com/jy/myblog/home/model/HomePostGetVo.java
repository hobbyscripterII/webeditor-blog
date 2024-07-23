package com.jy.myblog.home.model;

import lombok.Data;

@Data
public class HomePostGetVo {
    private int iboard;
    private int icategory;
    private String title;
    private String contents;
    private String nm;
    private String createdAt;
}
