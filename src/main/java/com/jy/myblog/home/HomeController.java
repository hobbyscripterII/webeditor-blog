package com.jy.myblog.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.jy.myblog.common.Const.POST;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(POST, service.getPost());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
