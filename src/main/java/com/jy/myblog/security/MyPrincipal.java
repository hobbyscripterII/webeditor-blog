package com.jy.myblog.security;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class MyPrincipal {
    private int iuser;
    private String role;
}
