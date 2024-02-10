package com.jy.myblog.user.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

// >>>>> spring security 적용 전 방법 - 사용 x >>>>>
//@Data
//public class UserLoginDto {
//    @NotEmpty(message = "아이디를 입력해주세요.")
//    private String uid;
//    @NotEmpty(message = "패스워드를 입력해주세요.")
//    private String upw;
//}