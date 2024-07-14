package com.jy.myblog.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BoardGetCntDto {
    private int icategory;
    private String keyword;
}
