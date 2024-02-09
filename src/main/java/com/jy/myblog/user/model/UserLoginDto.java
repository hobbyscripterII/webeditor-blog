package com.jy.myblog.user.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String uid;
    @NotEmpty(message = "패스워드를 입력해주세요.")
    private String upw;
}
