package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetSubjectVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.common.Pagination;
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
    public String admin(@RequestParam(name = "subject", required = false) Integer isubject, Pagination.Criteria criteria, Model model) {
        log.info("isubject = {}", isubject);
        int cnt = service.getPostCnt();

        List<AdminGetPostVo> list = service.getPostAdmin(criteria);
        List<AdminGetSubjectVo> subject = service.getSubject();
        Pagination pagination = new Pagination(criteria, cnt);

        model.addAttribute("list", list);
        model.addAttribute("subject", subject);
        model.addAttribute("pagination", pagination);
        return "/admin/admin";
    }

    @ResponseBody
    @PatchMapping("/public")
    public int updPublicFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updPublicFl(dto);
    }

    @ResponseBody
    @DeleteMapping()
    public int delPostFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.delPostFl(dto);
    }

    @ResponseBody
    @PatchMapping("/subject")
    public int updSubjectFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updSubjectFl(dto);
    }
}