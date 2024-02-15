package com.jy.myblog.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardGetVo {
    private int isubject;
    private String title;
    private String keyword;
    private List<BoardGetVo.Post> posts;

    @Data
    public static class Post {
        private int iboard;
        private String nm;
        private String title;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate createdAt;
    }
}
