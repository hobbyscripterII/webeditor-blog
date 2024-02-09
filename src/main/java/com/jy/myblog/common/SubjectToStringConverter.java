package com.jy.myblog.common;

import org.springframework.core.convert.converter.Converter;

public class SubjectToStringConverter implements Converter<Integer, String> {
    @Override
    public String convert(Integer source) {
        String subjectNm = null;
        switch (source) {
            case 4:
                subjectNm = Subject.JAVA.getSubjectNm();
                break;
            case 5:
                subjectNm = Subject.SPRING.getSubjectNm();
                break;
            case 6:
                subjectNm = Subject.DOCKER.getSubjectNm();
                break;
        }
        return subjectNm;
    }
}
