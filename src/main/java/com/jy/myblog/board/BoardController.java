package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.*;
import com.jy.myblog.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

import static com.jy.myblog.common.Const.FAIL;
import static com.jy.myblog.common.Const.SUCCESS;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService service;
    private final UploadUtil uploadUtil;

    // 웹에디터 이미지 첨부 시 해당 controller로 이동
    @RequestMapping("/imageupload")
    public ModelAndView imageUpload(@RequestParam(name = "iboard") int iboard, MultipartHttpServletRequest request) throws Exception {
        try {
            ModelAndView mv = new ModelAndView("jsonView");
            // local에 이미지 업로드 후 UUID + 확장자 얻어냄
            String uploadPath = uploadUtil.imageUpload(iboard, request);
            MultipartFile file = request.getFile("upload");
            String originalName = file.getOriginalFilename();
            String uuidName = uploadPath.substring(7); // db 저장용 이름 / resource 접근 경로 날림

            BoardInsPicDto dto = BoardInsPicDto.builder()
                    .iboard(iboard)
                    .originalName(originalName)
                    .uuidName(uuidName)
                    .build();

            int result = service.insPostPic(dto);

            if(Util.isNotNull(result)) {
                mv.addObject("uploaded", true);
                mv.addObject("url", uploadPath);
                return mv;
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            throw new Exception();
        }
    }

    @GetMapping("/list")
    public String getPost(@RequestParam(name = "subject", required = false, defaultValue = "0") int isubject, Pagination.Criteria criteria, Model model) {
        String title = null;

        if (Util.isNotNull(isubject)) {
            title = subjectToStringConverter(isubject);
        } else {
            title = "\'" + criteria.getKeyword() + "\' 검색 결과"; // 'N+1' 검색 결과
        }

        List<BoardGetVo.Post> posts = service.getPost(criteria);
        BoardGetVo list = new BoardGetVo(isubject, title, criteria.getKeyword(), posts);

        BoardGetCntDto dto = BoardGetCntDto
                .builder()
                .isubject(isubject)
                .keyword(criteria.getKeyword())
                .build();
        int cnt = service.getPostCnt(dto);
        Pagination pagination = new Pagination(criteria, cnt);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);
        return "/board/list";
    }

    @GetMapping("/read")
    public String selPost(@RequestParam(name = "subject") int isubject, @RequestParam(name = "board") int iboard, Model model) {
        String title = subjectToStringConverter(isubject);
        BoardSelVo post = service.selPost(iboard);
//        post.setContents(CommonUtil.markdown(post.getContents())); // 마크다운 치환

        model.addAttribute("title", title);
        model.addAttribute("post", post);
        return "/board/read";
    }

    @GetMapping("/write")
    public String insPost(@RequestParam(name = "subject") int isubject, Model model) {
        try {
            String title = subjectToStringConverter(isubject);
            BoardInsDto dto = BoardInsDto.builder()
                    .iuser(getUserPk())
                    .isubject(isubject)
                    .build();

            service.insNullPost(dto);
            int iboard = dto.getIboard();

            model.addAttribute("dto", new BoardInsDto());
            model.addAttribute("title", title);
            model.addAttribute("iboard", iboard);
            model.addAttribute("subject", isubject); // *

            return "/board/write_webeditor";
        } catch (Exception e) {
            throw new RuntimeException("failed insert post", e);
        }
    }



    @GetMapping("/update")
    public String updPost(@RequestParam(name = "board") int iboard, Model model) {
        model.addAttribute("dto", service.selPost(iboard));
        return "/board/write_webeditor";
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
    public int delPost(@RequestParam(name = "iboard") int iboard) throws Exception {
        try {
            if (Util.isNotNull(service.delPost(iboard))) {
                uploadUtil.delDirTrigger(iboard);
                return SUCCESS;
            } else {
                throw new Exception(); // rollback
            }
        } catch(Exception e) {
            throw new Exception();
        }
    }

    // spring security session에 저장된 userPk
    public int getUserPk() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        return myUserDetails.getIuser();
    }

    // subject int -> String 타입으로 converter
    public String subjectToStringConverter(int isubject) {
        return new SubjectToStringConverter().convert(isubject);
    }

//    @ResponseBody
//    @GetMapping("/tag")
//    public List<BoardTagGetVo> getTag(@RequestParam(name = "tag") String tag) {
//        return service.getTag(tag);
//    }

    // >>>>> markdown
//    @GetMapping("/write")
//    public String insPost(@RequestParam(name = "subject") int isubject, Model model) {
//        String title = subjectToStringConverter(isubject);
//
//        model.addAttribute("dto", new BoardInsDto());
//        model.addAttribute("title", title);
//        model.addAttribute("subject", isubject);
//        return "/board/write";
//    }

//    @ResponseBody
//    @PostMapping("/write")
//    public int insPost(@RequestBody BoardInsDto dto) throws Exception {
//        try {
//            dto.setIuser(getUserPk());
//
//            if (Util.isNotNull(service.insPost(dto))) {
//                return dto.getIboard();
//            } else {
//                return FAIL;
//            }
//        } catch (Exception e) {
//            throw new Exception();
//        }
//    }
}