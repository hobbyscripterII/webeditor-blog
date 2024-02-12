package com.jy.myblog.admin.model;

import com.jy.myblog.common.Const;
import com.jy.myblog.common.SubjectToStringConverter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AdminPostGetVo {
    private int iboard;
    private String publicFl;
    private int isubject;
    public String subject;
    private String nm;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    public String getPublicFl() {
        return publicFl.equals("Y") ? Const.PUBLIC : Const.PRIVATE;
    }

//    public String getSubject() {
//        return new SubjectToStringConverter().convert(isubject);
//    }
}
