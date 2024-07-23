package com.jy.myblog.board;

import com.jy.myblog.board.model.*;
import com.jy.myblog.common.*;
import com.jy.myblog.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import java.nio.file.Paths;
import java.util.List;

import static com.jy.myblog.common.Const.*;

// * 예외 처리 수정 필요

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService service;
    private final UploadUtil uploadUtil;

    @GetMapping("/file/download/{iboardfile}")
    public ResponseEntity<Resource> download(@PathVariable(name = "iboardfile") int iboardfile) {
        try {
            BoardSelVo.File file = service.selPostFile(iboardfile);
            String downloadPath = uploadUtil.getDownloadPath(file.getUuidName());
            Resource resource = new UrlResource(Paths.get(downloadPath).toUri());

            return ResponseEntity.ok()
                                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriUtils.encode(file.getOriginalName(), "UTF-8") + "\"")
                                 .body(resource);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // 웹에디터 이미지 첨부 시
    @RequestMapping("/imageupload")
    public ModelAndView imageUpload(@RequestParam(name = "iboard") int iboard, MultipartHttpServletRequest request) throws Exception {
        try {
            ModelAndView mv = new ModelAndView("jsonView");
            String path = "image/" + iboard; // 이미지 저장시엔 'image' 경로 반환 / 파일 업로드는 'file' 경로 반환
            String uploadPath = uploadUtil.imageUpload(path, request); // local에 이미지 업로드 후 UUID + 확장자 얻어냄
            MultipartFile file = request.getFile("upload");
            String originalName = file.getOriginalFilename();
            int rows = service.insPostPic(BoardInsPicDto.builder()
                                                        .iboard(iboard)
                                                        .originalName(originalName)
                                                        .uuidName(uploadPath)
                                                        .build());

            if (Util.isNotNull(rows)) {
                mv.addObject("uploaded", true);
                mv.addObject("url", uploadPath);
                return mv;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(); // 수정 필요
        }
    }

    @PostMapping("/comment/delete")
    @ResponseBody
    public int delComment(BoardCommentDelDto dto) {
        return service.delComment(dto);
    }

    @PostMapping("/comment/update")
    @ResponseBody
    public int updComment(BoardCommentUpdDto dto) {
        return service.updComment(dto);
    }

    @PostMapping("/comment/write")
    @ResponseBody
    public int insComment(BoardCommentInsDto dto) {
        return service.insComment(dto);
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(VO, service.getBoardHomePost());
        return "board/home";
    }

    @GetMapping("/list")
    public String getPost(@RequestParam(name = "category", required = false, defaultValue = "0") int icategory, Pagination.Criteria criteria, Model model) {
        String title = null;
        criteria.setIcategory(icategory);

        if (Util.isNotNull(icategory)) { title = categoryToStringConverter(icategory); }
        else { title = "\'" + criteria.getKeyword() + "\' 검색 결과"; }

        List<BoardGetVo.Post> posts = service.getPost(criteria);
        BoardGetVo list = new BoardGetVo(icategory, title, criteria.getKeyword(), posts);
        int cnt = service.getPostCnt(BoardGetCntDto.builder()
                                                   .icategory(icategory)
                                                   .keyword(criteria.getKeyword())
                                                   .build());
        Pagination pagination = new Pagination(criteria, cnt);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);
        return "board/list";
    }

    @GetMapping("/read")
    public String selPost(@RequestParam(name = "category") int icategory, @RequestParam(name = "post") int iboard, Model model) {
        String title = categoryToStringConverter(icategory);
        BoardSelVo post = service.selPost(iboard);
        model.addAttribute("title", title);
        model.addAttribute("post", post);
        return "board/read";
    }

    @GetMapping("/write")
    public String insPost(@RequestParam(name = "category") int icategory, Model model) {
        try {
            String title = categoryToStringConverter(icategory);
            BoardInsDto dto = BoardInsDto.builder()
                                         .iuser(getUserPk())
                                         .icategory(icategory)
                                         .build();
            service.insNullPost(dto);

            model.addAttribute("dto", new BoardInsDto());
            model.addAttribute("title", title);
            model.addAttribute("iboard", dto.getIboard());
            model.addAttribute("category", icategory);
            model.addAttribute("subcategory", service.getSubCategory(icategory));

            return "board/write";
        } catch (Exception e) {
            throw new RuntimeException("failed insert post", e); // 수정 필요
        }
    }

    @GetMapping("/update")
    public String updPost(@RequestParam(name = "board") int iboard, @RequestParam(name = "category") int icategory, Model model) {
        model.addAttribute("dto", service.selPost(iboard));
        model.addAttribute("category", icategory);
        model.addAttribute("subcategory", service.getSubCategory(icategory));

        return "board/write";
    }

    @PutMapping
    @ResponseBody
    public int updPost(@RequestPart("dto") BoardUpdDto dto, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            int updPostRows = service.updPost(dto);

            if (Util.isNotNull(updPostRows)) {
                if (file != null) {
                    String path = "file/" + dto.getIboard();
                    String uploadPath = uploadUtil.fileUpload(path, file);
                    String originalName = file.getOriginalFilename();
                    int rows = service.insPostFile(BoardInsFileDto.builder()
                            .iboard(dto.getIboard())
                            .originalName(originalName)
                            .uuidName(uploadPath)
                            .build());
                }

                // >>>>> 이미지 수정 fl
                // pk로 db에 저장된 사진 uuid 가져옴
                List<String> getPostPics = service.getPostPics(dto.getIboard());
                // 해당 게시글에 없는 사진만 뽑아냄(db, 디렉토리 정리용)
                List<String> isNullPics = getPostPics.stream()
                        // 글 내용에 uuid가 없으면 사용자가 사진을 삭제한 것이므로 list에 담음
                        .filter(pic -> !dto.getContents().contains(pic))
                        .toList(); // 후처리

                if (Util.isNotNull(isNullPics.size())) {
                    // 해당 게시글에 없는 사진만 삭제
                    for (String pic : isNullPics) {
                        service.delPostPic(pic); // DB 사진 삭제
                        uploadUtil.deleteFile(String.valueOf(Paths.get(pic))); // 디렉토리 사진 삭제
                    }
                }

                return dto.getIboard();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return FAIL;
        }
    }

    @DeleteMapping
    @ResponseBody
    public int delPost(@RequestParam(name = "iboard") int iboard) {
        try {
            if (Util.isNotNull(service.delPost(iboard))) {
                uploadUtil.delDirTrigger("/image/" + iboard);
                uploadUtil.delDirTrigger("/file/" + iboard);
                return SUCCESS;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return FAIL;
        }
    }

    // Spring Security Session에 저장된 회원 PK
    public int getUserPk() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        return myUserDetails.getIuser();
    }

    public String categoryToStringConverter(int isubject) {
        return new CategoryToStringConverter().convert(isubject);
    }
}