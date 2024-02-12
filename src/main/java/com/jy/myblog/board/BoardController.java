package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.CommonUtil;
import com.jy.myblog.common.PageNation;
import com.jy.myblog.common.SubjectToStringConverter;
import com.jy.myblog.common.Util;
import com.jy.myblog.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.jy.myblog.common.Const.FAIL;
import static com.jy.myblog.common.Const.SUCCESS;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService service;

    @GetMapping("/list")
    public String getPost(PageNation.Criteria criteria, @RequestParam(name = "subject") int isubject, Model model) {
        String title = subjectToStringConverter(isubject);
        List<BoardGetVo.Post> posts = service.getPost(criteria);
        BoardGetVo list = new BoardGetVo(isubject, title, posts);

        int cnt = service.getPostCnt(isubject);
        PageNation pageNation = new PageNation(criteria, cnt);

        model.addAttribute("list", list);
        model.addAttribute("pageNation", pageNation);
        return "/board/list";
    }

    @GetMapping("/read")
    public String selPost(@RequestParam(name = "subject") int isubject, @RequestParam(name = "board") int iboard, Model model) {
        String title = subjectToStringConverter(isubject);
        BoardSelVo post = service.selPost(iboard);
        post.setContents(CommonUtil.markdown(post.getContents())); // 마크다운 치환

        model.addAttribute("title", title);
        model.addAttribute("post", post);
        return "/board/read";
    }

    @GetMapping("/write")
    public String insPost(@RequestParam(name = "subject") int isubject, Model model) {
        String title = subjectToStringConverter(isubject);
        model.addAttribute("dto", new BoardInsDto());
        model.addAttribute("title", title);
        model.addAttribute("subject", isubject);
        return "/board/write";
    }

    @ResponseBody
    @PostMapping("/write")
    public int insPost(@RequestBody BoardInsDto dto) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            dto.setIuser(myUserDetails.getIuser());

            if (Util.isNotNull(service.insPost(dto))) {
                return dto.getIboard();
            } else {
                return FAIL;
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @GetMapping("/update")
    public String updPost(@RequestParam(name = "board") int iboard, Model model) {
        model.addAttribute("dto", service.selPost(iboard));
        return "/board/write";
    }

    @ResponseBody
    @PutMapping
    public int updPost(@RequestBody BoardUpdDto dto) {
        if (Util.isNotNull(service.updPost(dto))) {
            return dto.getIboard();
        } else {
            return FAIL;
        }
    }

    @ResponseBody
    @DeleteMapping
    public int delPost(@RequestParam(name = "iboard") int iboard) {
        if (Util.isNotNull(service.delPost(iboard))) {
            return SUCCESS;
        }
        return FAIL;
    }

    public String subjectToStringConverter(int isubject) {
        return new SubjectToStringConverter().convert(isubject);
    }

    @ResponseBody
    @GetMapping("/tag")
    public List<BoardTagGetVo> getTag(@RequestParam(name = "tag") String tag) {
        return service.getTag(tag);
    }
}