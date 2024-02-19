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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import java.nio.file.Paths;
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

    @GetMapping("/file/download")
    public ResponseEntity<Resource> download(@RequestParam(name = "iboardfile") int iboardfile) throws Exception {
        try {
            log.info("iboardfile = {}", iboardfile);
            BoardSelVo.File file = service.selPostFile(iboardfile);
            log.info("file = {}", file);
            String downloadPath = uploadUtil.getDownloadPath(file.getUuidName());
            Resource resource = new UrlResource(Paths.get(downloadPath).toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriUtils.encode(file.getOriginalName(), "UTF-8") + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // 웹에디터 이미지 첨부 시 해당 controller로 이동
    @RequestMapping("/imageupload")
    public ModelAndView imageUpload(@RequestParam(name = "iboard") int iboard, MultipartHttpServletRequest request) throws Exception {
        try {
//            uploadUtil.delDirTrigger(iboard); // 글 등록, 수정 시 원래있던 사진 삭제 후 재업로드

            ModelAndView mv = new ModelAndView("jsonView");
            String path = "image/" + iboard; // 이미지 저장시엔 'image' 경로 반환 / 파일 업로드는 'file' 경로 반환
            // local에 이미지 업로드 후 UUID + 확장자 얻어냄
            String uploadPath = uploadUtil.imageUpload(path, request);
            MultipartFile file = request.getFile("upload");
            String originalName = file.getOriginalFilename();
//            String uuidName = uploadPath.substring(6); // db 저장용 이름 / resource 접근 경로 날림

            BoardInsPicDto dto = BoardInsPicDto.builder()
                    .iboard(iboard)
                    .originalName(originalName)
                    .uuidName(uploadPath)
                    .build();

            int rows = service.insPostPic(dto);

            if (Util.isNotNull(rows)) {
                mv.addObject("uploaded", true);
                mv.addObject("url", uploadPath);
                return mv;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
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
            // >>>>> 첨부파일 구현 후 주석 해제
            // 글 작성 버튼 클릭 시 pk 값 +
            BoardInsDto dto = BoardInsDto.builder()
                    .iuser(getUserPk())
                    .isubject(isubject)
                    .build();

            service.insNullPost(dto);
            int iboard = dto.getIboard();

            model.addAttribute("dto", new BoardInsDto());
            model.addAttribute("title", title);
            model.addAttribute("iboard", iboard); // >>>>> 첨부파일 구현 후 주석 해제
//            model.addAttribute("iboard", 1000); // >>>>> 첨부파일 테스터용
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
    public int updPost(@RequestPart("dto") BoardUpdDto dto, @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        try {
            int result = service.updPost(dto);

            // >>>>> 첨부파일 fl
            if (file != null) {
                String path = "file/" + dto.getIboard();
                String uploadPath = uploadUtil.fileUpload(path, file);
                String originalName = file.getOriginalFilename();
//            String uuidName = uploadPath.substring(4); // db 저장용 이름 / resource 접근 경로 날림

                BoardInsFileDto insFileDto = BoardInsFileDto.builder()
                        .iboard(dto.getIboard())
                        .originalName(originalName)
                        .uuidName(uploadPath)
                        .build();

                int rows = service.insPostFile(insFileDto);
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
                    service.delPostPic(pic); // db 사진 삭제
                    uploadUtil.deleteFile(String.valueOf(Paths.get(pic))); // 디렉토리 사진 삭제
                }
            }

            // >>>>> 추후 첨부파일 수정 fl 추가


            return dto.getIboard();
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

//    @ResponseBody
//    @PutMapping
//    public int updPost(@RequestBody BoardUpdDto dto, @RequestParam(name = "file") MultipartFile file) throws Exception {
//        try {
//            log.info("dto = {}", dto);
//            log.info("file = {}", file);
//
////            int result = service.updPost(dto);
////            // pk로 db에 저장된 사진 uuid 가져옴
////            List<String> getPostPics = service.getPostPics(dto.getIboard());
////            // 해당 게시글에 없는 사진만 뽑아냄(db, 디렉토리 정리용)
////            List<String> isNullPics = getPostPics.stream()
////                    // 글 내용에 uuid가 없으면 사용자가 사진을 삭제한 것이므로 list에 담음
////                    .filter(pic -> !dto.getContents().contains(pic))
////                    .collect(toList()); // 후처리
////
////            // 해당 게시글에 없는 사진만 삭제
////            for (String pic : isNullPics) {
////                service.delPostPic(pic); // db 사진 삭제
////                uploadUtil.deleteFile(String.valueOf(Paths.get(pic))); // 디렉토리 사진 삭제
////            }
////            return dto.getIboard();
//            return SUCCESS;
//
//        } catch (Exception e) {
//            return FAIL;
//        }
//    }

    @ResponseBody
    @DeleteMapping
    public int delPost(@RequestParam(name = "iboard") int iboard) throws Exception {
        try {
            if (Util.isNotNull(service.delPost(iboard))) {
                uploadUtil.delDirTrigger("/image/" + iboard);
                uploadUtil.delDirTrigger("/file/" + iboard);
                return SUCCESS;
            } else {
                return FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
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