package com.jy.myblog.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardInsDto {
    private int iboard;
    private int icategory;
    private int isubcategory;
    private int iuser;
    private String subCategoryNm;
    private String title;
    private String contents;
}
