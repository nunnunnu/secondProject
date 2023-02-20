package com.secondproject.project.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(description="이미지파일", name="이미지")
public class FileController {
    
    private final FileService fService;

    @Operation(summary = "게시판 이미지 다운로드 링크입니다.", description ="게시판 상세조회에서 확인가능한 uri를 넣어주시면 해당 이미지를 다운 가능합니다.")
    @GetMapping("/images/{uri}") 
    public ResponseEntity<Resource> getImage ( @Parameter(description = "이미지 uri") @PathVariable String uri, 
            HttpServletRequest request ) throws Exception { 
                return fService.getImage(uri, request);
    }
}
