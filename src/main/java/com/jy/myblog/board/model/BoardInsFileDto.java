package com.jy.myblog.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class BoardInsFileDto {
    private int iboard;
    private String originalName;
    private String uuidName;
}
