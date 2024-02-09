package com.jy.myblog.board;

import com.jy.myblog.board.model.BoardGetDto;
import com.jy.myblog.board.model.BoardGetVo;
import com.jy.myblog.board.model.BoardSelVo;
import com.jy.myblog.common.CommonUtil;
import com.jy.myblog.common.SubjectToStringConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService service;

    @GetMapping("/list")
    public String getPost(@RequestParam(name = "subject") int isubject, Model model) {
        String title = new SubjectToStringConverter().convert(isubject);
        List<BoardGetVo> list = service.getPost(isubject);
        model.addAttribute("title", title);
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
}