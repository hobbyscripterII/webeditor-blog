package com.jy.myblog.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// 사용 x
@Data
@AllArgsConstructor
public class BoardGetDto {
    private int category;
    private int subject;
}
