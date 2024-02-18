package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetSubjectVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.common.Const;
import com.jy.myblog.common.Pagination;
import com.jy.myblog.common.UploadUtil;
import com.jy.myblog.common.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.jy.myblog.common.Const.FAIL;
import static com.jy.myblog.common.Const.SUCCESS;

@Slf4j
@Controller
@RequiredArgsConstructor
//@Secured("ROLE_ADMIN") // 권한이 ADMIN인 사용자만 접근 o
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;
    private final UploadUtil uploadUtil;

    @GetMapping
    public String admin(@RequestParam(name = "subject", required = false, defaultValue = "0") int isubject, Pagination.Criteria criteria, Model model) {
        int cnt = service.getPostCnt(isubject);
        List<AdminGetPostVo> list = service.getPostAdmin(criteria);
        List<AdminGetSubjectVo> subjects = service.getSubject();
        Pagination pagination = new Pagination(criteria, cnt);

        model.addAttribute("list", list);
        model.addAttribute("subject", criteria.getSubject());
        model.addAttribute("subjects", subjects);
        model.addAttribute("pagination", pagination);
        return "/admin/admin";
    }

    @ResponseBody
    @PatchMapping("/public")
    public int updPublicFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updPublicFl(dto);
    }

    @ResponseBody
    @DeleteMapping
    public int delPostFl(@RequestBody AdminUpdDto dto) throws Exception {
        try {
            int result = service.delPostFl(dto);

            log.info("result = {}", result);
            log.info("dto = {}", dto);

            if (Util.isNotNull(result)) {
                for (Integer iboard : dto.getList()) {
                    uploadUtil.delDirTrigger("/image/" + iboard);
                    uploadUtil.delDirTrigger("/file/" + iboard);
                }
                return SUCCESS;
            } else {
                return FAIL;
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @ResponseBody
    @PatchMapping("/subject")
    public int updSubjectFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updSubjectFl(dto);
    }
}