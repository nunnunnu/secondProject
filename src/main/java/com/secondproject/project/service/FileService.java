package com.secondproject.project.service;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.secondproject.project.entity.BoardImageEntity;
import com.secondproject.project.repository.BoardImageRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${file.image.board}") String board_img_path;
    private final BoardImageRepository bimgRepo;
    
    public ResponseEntity<Resource> getImage ( @PathVariable String uri, 
        HttpServletRequest request ) throws Exception 
    { 
        Path folderLocation = null;
        String filename = null;
        // 내보낼 파일의 이름을 만든다. 
        // 폴더 경로와 파일의 이름을 합쳐서 목표 파일의 경로를 만든다. 
        
        folderLocation = Paths.get(board_img_path);
        BoardImageEntity image = bimgRepo.findByBimgName(uri);
        filename = image.getBimgName();
        String[] split = filename.split("\\.");
        String ext = split[split.length - 1];
        String exportName = image.getBimgUri() + "." + ext;
        
        Path targerFile = folderLocation.resolve(filename); //폴더 경로와 파일의 이름을 합쳐서 목표 파일의 경로 생성
        //다운로드 가능한 형태로 변환하기 위해 Resource객체 생성함
        Resource r = null;
        try {
            // 일반파일 -> Url로 첨부 가능한 형태로 변환 
            r = new UrlResource(targerFile.toUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 첨부된 파일의 타입을 저장하기위한 변수 생성 
        String contentType = null;
        try {
            // 첨부할 파일의 타입 정보 산출 
            contentType = request.getServletContext().getMimeType(r.getFile().getAbsolutePath());
            // 산출한 파일의 타입이 null 이라면 
            if (contentType == null) {
                // 일반 파일로 처리한다. 
                contentType = "application/octet-stream";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
            // 응답의 코드를 200 OK로 설정하고 
            // 산출한 타입을 응답에 맞는 형태로 변환 
            .contentType(MediaType.parseMediaType(contentType))
            // 내보낼 내용의 타입을 설정 (파일), 
            // attachment; filename*=\""+r.getFilename()+"\" 요청한 쪽에서 다운로드 한 
            // 파일의 이름을 결정 
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(exportName, "UTF-8") + "\"")
            .body(r);
        // 변환된 파일을 ResponseEntity에 추가 }
    }

    public BoardImageEntity saveImageFile(MultipartFile file){
        Path folderLocation = Paths.get(board_img_path);

        String originFileName = file.getOriginalFilename();
        String[] split = originFileName.split(("\\.")); //.을 기준으로 나눔
        String ext = split[split.length - 1]; //확장자
        String fileName = "";
        for (int i = 0; i < split.length - 1; i++) {
            fileName += split[i]; //원래 split[i]+"." 이렇게 해줘야함
        }
        String saveFileName = "board_"; //보통 원본 이름을 저장하는것이아니라 시간대를 입력함
        Calendar c = Calendar.getInstance();
        saveFileName += c.getTimeInMillis() + "." + ext; // todo_161310135.png 이런식으로 저장됨

        Path targetFile = folderLocation.resolve(saveFileName); //폴더 경로와 파일의 이름을 합쳐서 목표 파일의 경로 생성
        try {
            //Files는 파일 처리에 대한 유틸리티 클래스
            //copy - 복사, file.getInputStream() - 파일을 열어서 파일의 내용을 읽는 준비
            //targetFile 경로로, standardCopyOption.REPLACE_EXISTING - 같은 파일이 있다면 덮어쓰기.
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BoardImageEntity.builder().bimgName(saveFileName).bimgUri(fileName).build();
    }
    
}
