package com.jy.myblog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Subject {
    JAVA(4, "JAVA"),
    SPRING(5, "SPRING"),
    DOCKER(6, "DOCKER");

    private int subjectNum;
    private String subjectNm;
}
