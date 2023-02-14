package com.secondproject.project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.secondproject.project.service.BoardService;
import com.secondproject.project.vo.board.BoardUpdateVO;
import com.secondproject.project.vo.board.BoardinsertVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(description="게시판관련 기능", name="게시판")
public class BoardController {
    private final BoardService bService;

    @Operation(summary = "게시글 등록", description ="게시글을 등록합니다. (form-data로 보내주세요)")
    @PutMapping("/add/{seq}")
    public ResponseEntity<Object> saveBoard(
        @Parameter(description = "회원 번호") @PathVariable Long seq,
        @Parameter(description = "등록할 게시글 정보") BoardinsertVO data,
        @Parameter(description = "게시글에 첨부할 파일(null가능, 같은 변수이름으로 여러개 등록 가능합니다.)") @Nullable MultipartFile... img //폼데이터로 받는 방법임
    ){
        Map<String, Object> map = bService.addBoard(seq, data, img);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }

    @PostMapping("/update/{member}/{post}")
    public ResponseEntity<Object> updateBoard(
        @Parameter(description = "회원번호") @PathVariable Long member,
        @Parameter(description = "수정할 게시글 번호") @PathVariable Long post,
        @Parameter(description = "수정할 내용") @Nullable BoardUpdateVO data,
        @Parameter(description = "게시글에 추가로 첨부할 파일(null가능)") @Nullable MultipartFile... img //폼데이터로 받는 방법임
    ){
        Map<String, Object> map = bService.updateBoard(member, post, data, img);
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @DeleteMapping("/delete/{member}/{post}")
    public ResponseEntity<Object> deleteBoard(
        @Parameter(description = "회원번호") @PathVariable Long member,
        @Parameter(description = "삭제할 게시글 번호") @PathVariable Long post
    ){
        Map<String, Object> map = bService.deleteBoard(member, post);
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
}
