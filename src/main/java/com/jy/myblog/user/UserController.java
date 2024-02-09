package com.jy.myblog.user;

import com.jy.myblog.user.model.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    // >>>>> spring security 사용으로 주석 처리
//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("dto", new UserLoginDto());
//        return "/login";
//    }

//    @PostMapping("/login")
//    public String login(@Validated @ModelAttribute(name = "dto") UserLoginDto dto, BindingResult bindingResult) {
//        boolean login = service.login(dto);
//
//        if(!login) {
//            bindingResult.addError(new ObjectError("dto", "아이디 혹은 패스워드를 확인해주세요."));
//        }
//
//        if (bindingResult.hasErrors()) {
//            return "/login";
//        }
//
//        return "/";
//    }
}
