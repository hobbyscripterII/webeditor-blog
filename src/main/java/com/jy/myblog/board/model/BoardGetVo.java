package com.jy.myblog.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardGetVo {
    private int icategory;
    private String title;
    private String keyword;
    private List<BoardGetVo.Post> posts;

    @Data
    public static class Post {
        private int iboard;
        private String nm;
        private String subCategoryNm;
        private String title;
        private int commentCnt;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate createdAt;
    }
}