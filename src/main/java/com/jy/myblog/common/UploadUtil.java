package com.jy.myblog.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.jy.myblog.common.Const.SUCCESS;

@Slf4j
@Component
public class UploadUtil {
    private final String prefixPath;

    public UploadUtil(@Value("${file.path}") String prefixPath) {
        this.prefixPath = prefixPath;
    }

    public String getDownloadPath(String uuidName) {
        uuidName = uuidName.substring(7);
        Path path = Paths.get(prefixPath, uuidName);
        return String.valueOf(path.toAbsolutePath());
    }

    public String imageUpload(String path, MultipartHttpServletRequest request) {
        MultipartFile uploadFile = request.getFile("upload");
        String fileName = getFileName(uploadFile);
        String realPath = getPath(path);
        Path savePath_ = Paths.get(realPath, fileName);
        String savePath = String.valueOf(savePath_);
        // webconfig에서 해당 경로로 접근하면 외부 리소스에 접근시켜놓음
        // 외부 리소스 접근 시 /upload/는 yaml에 설정한 경로까지만 접근하기 때문에 뒤에 이동 경로를 지정해주지 않으면 resource 접근 x
        String uploadPath = "/upload/" + path + "/" + fileName;
        uploadFile(savePath, uploadFile);
        return uploadPath;
    }

    public String fileUpload(String path, MultipartFile uploadFile) {
        try {
            String fileName = getFileName(uploadFile);
            String realPath = getPath(path);
            Path savePath_ = Paths.get(realPath, fileName);
            String savePath = String.valueOf(savePath_);
            String uploadPath = "/upload/" + path + "/" + fileName;
            uploadFile(savePath, uploadFile);
            return uploadPath;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 파일, 이미지 저장
    private void uploadFile(String savePath, MultipartFile multipartFile) {
        File file = new File(savePath);

        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            throw new RuntimeException("failed to upload the file", e);
        }
    }

    // 경로 반환
    private String getPath(String path) {
        Path directoryPath_ = Paths.get(prefixPath, path);
        String directoryPath = String.valueOf(directoryPath_.toAbsolutePath());

        if (!Files.exists(directoryPath_)) { // 해당 경로에 디렉토리 없으면 생성
            try {
                Files.createDirectories(directoryPath_);
            } catch (Exception e) { // 생성 불가능할 경우 예외 던짐
                throw new RuntimeException("could not create upload directory", e);
            }
        }
        return directoryPath;
    }

    private String getFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + ext;
    }

    // 디렉토리 + 파일 삭제 트리거
    public void delDirTrigger(String suffixPath) {
        deleteDirectory(prefixPath + suffixPath);
    }

    // '하위 디렉토리 > 파일' 삭제
    public void deleteDirectory(String path) { // path - 풀 경로
        File dir = new File(path); // 파일 객체 생성
        if (dir.exists()) { // exists() - 디렉토리 존재 여부 확인

            File[] files = dir.listFiles(); // 해당 디렉토리에 있는 모든 파일을 리스트에 담음

            // files에 담긴 파일이 디렉토리일 경우 delFiles를 다시 호출해(재귀함수) 해당 디렉토리의 파일을 다시 리스트에 담음
            // 하위 디렉토리에 파일만 있을 경우 해당 파일들을 모두 삭제
            for (File file : files) {
                if (file.isDirectory()) { // isDirectory() - 해당 파일이 폴더인지 확인
                    deleteDirectory(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
            dir.delete();
        }
    }

    public int deleteFile(String suffixPath) throws Exception {
        try {
            File file = new File(prefixPath + suffixPath);
            file.delete();
            return SUCCESS;

        } catch (Exception e) {
            throw new Exception();
        }
    }
}
