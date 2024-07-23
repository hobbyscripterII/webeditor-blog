package com.jy.myblog.common;

import org.springframework.core.convert.converter.Converter;

public class CategoryToStringConverter implements Converter<Integer, String> {
    @Override
    public String convert(Integer source) {
        String categoryNm = null;
        switch (source) {
            case 1:
                categoryNm = Category.NOTICE.getCategoryNm();
                break;
            case 2:
                categoryNm = Category.STUDY.getCategoryNm();
                break;
            case 3:
                categoryNm = Category.DIARY.getCategoryNm();
                break;
            case 4:
                categoryNm = Category.SONY_A6000.getCategoryNm();
                break;
            case 5:
                categoryNm = Category.GUEST_BOOK.getCategoryNm();
                break;
        }
        return categoryNm;
    }
}
