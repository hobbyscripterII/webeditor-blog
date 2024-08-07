package com.jy.myblog.admin;

import com.jy.myblog.admin.model.AdminGetPostVo;
import com.jy.myblog.admin.model.AdminGetCategoryVo;
import com.jy.myblog.admin.model.AdminUpdDto;
import com.jy.myblog.common.Pagination;
import com.jy.myblog.common.UploadUtil;
import com.jy.myblog.common.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
    public String admin(@RequestParam(name = "category", required = false, defaultValue = "0") int icategory, Pagination.Criteria criteria, Model model) {
        int cnt = service.getPostCnt(icategory);
        criteria.setIcategory(icategory);
        List<AdminGetPostVo> list = service.getPostAdmin(criteria);
        List<AdminGetCategoryVo> categories = service.getCategory();
        Pagination pagination = new Pagination(criteria, cnt);

        model.addAttribute("list", list);
        model.addAttribute("category", criteria.getIcategory());
        model.addAttribute("categories", categories);
        model.addAttribute("pagination", pagination);
        return "admin/admin";
    }

    @ResponseBody
    @PatchMapping("/public")
    public int updPublicFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updPublicFl(dto);
    }

    @ResponseBody
    @PatchMapping
    public int delPostFl(@RequestBody AdminUpdDto dto) throws Exception {
        try {
            if (Util.isNotNull(service.delPostFl(dto))) {
                return SUCCESS;
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            return FAIL;
        }
    }

    @ResponseBody
    @PatchMapping("/subject")
    public int updSubjectFl(@RequestBody AdminUpdDto dto) throws Exception {
        return service.updSubjectFl(dto);
    }
}