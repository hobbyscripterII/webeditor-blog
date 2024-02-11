package com.jy.myblog.board;

import com.jy.myblog.board.model.BoardGetVo;
import com.jy.myblog.board.model.BoardInsDto;
import com.jy.myblog.board.model.BoardSelVo;
import com.jy.myblog.board.model.BoardTagGetVo;
import com.jy.myblog.common.CommonUtil;
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

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService service;

    @GetMapping("/list")
    public String getPost(@RequestParam(name = "subject") int isubject, Model model) {
        String title = new SubjectToStringConverter().convert(isubject);
        List<BoardGetVo.Post> post = service.getPost(isubject);
        BoardGetVo list = new BoardGetVo();
        list.setPosts(post);
        list.setTitle(title);
        list.setIsubject(isubject);
        model.addAttribute("list", list);
        return "/board/list";
    }

    @GetMapping("/read")
    public String selPost(@RequestParam(name = "subject") int isubject, @RequestParam(name = "board") int iboard, Model model) {
        String title = new SubjectToStringConverter().convert(isubject);
        BoardSelVo post = service.selPost(iboard);
        post.setContents(CommonUtil.markdown(post.getContents())); // 마크다운 치환
        model.addAttribute("title", title);
        model.addAttribute("post", post);
        return "/board/read";
    }

    @GetMapping("/write")
    public String insPost(@RequestParam(name = "subject") int isubject, Model model) {
        String title = new SubjectToStringConverter().convert(isubject);
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

    @ResponseBody
    @GetMapping("/tag")
    public List<BoardTagGetVo> getTag(@RequestParam(name = "tag") String tag) {
        return service.getTag(tag);
    }
}