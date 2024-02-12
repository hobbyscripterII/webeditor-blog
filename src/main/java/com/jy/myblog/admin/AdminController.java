package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminUpdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
//@Secured("ROLE_ADMIN") // 권한이 ADMIN인 사용자만 접근 o
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("list", service.getPostAdmin());
        model.addAttribute("subject", service.getSubject());
        return "/admin/admin";
    }

    @ResponseBody
    @PatchMapping("/public")
    public int updPublicFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updPublicFl(dto);
    }

    @ResponseBody
    @PatchMapping("/subject")
    public int updSubjectFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updSubjectFl(dto);
    }
}