package com.jy.myblog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    NOTICE(1, Const.NOTICE),
    DEV(2, Const.DEV),
    DIARY(3, Const.DIARY),
    SONY_A6000(4, Const.SONY_A6000),
    GUEST_BOOK(5, Const.GUEST_BOOK);

    private int categoryNum;
    private String categoryNm;
}