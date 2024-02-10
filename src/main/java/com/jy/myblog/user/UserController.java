package com.jy.myblog.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    // 권한 없을 경우 해당 페이지 출력 - (예) admin ...
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    // >>>>> spring security 적용 전 방법 - 사용 x >>>>>
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
