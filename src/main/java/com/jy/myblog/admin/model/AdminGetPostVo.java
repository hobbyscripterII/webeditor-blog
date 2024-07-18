package com.jy.myblog.admin.model;

import com.jy.myblog.common.Const;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AdminGetPostVo {
    private int iboard;
    private int icategory;
    private String publicFl;
    public String categoryNm;
    public String subCategoryNm;
    private String nm;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    public String getPublicFl() {
        return publicFl.equals("Y") ? Const.PUBLIC : Const.PRIVATE;
    }
}
