package com.jy.myblog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Subject {
    JAVA(4, Const.JAVA),
    SPRING(5, Const.SPRING),
    DOCKER(6, Const.DOCKER);

    private int subjectNum;
    private String subjectNm;
}