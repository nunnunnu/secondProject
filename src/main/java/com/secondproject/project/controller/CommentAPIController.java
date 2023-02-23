package com.secondproject.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.service.CommentService;
import com.secondproject.project.vo.CommentAddVO;
import com.secondproject.project.vo.CommentUpdateVO;
import com.secondproject.project.vo.MapVO;
import com.secondproject.project.vo.UpperCommentVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "댓글 관리", description = "댓글 API")
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentAPIController {
    private final CommentService commentService;

    // 댓글등록
    @Operation(summary = "댓글등록", description = "member : 회원번호, biSeq : 게시판번호")
    @PutMapping("/add/{member}/{biSeq}")
    public ResponseEntity<MapVO> addComment(
        @Parameter(description = "회원번호 ex member:1" )
        @PathVariable Long member,
        @Parameter(description = "게시판번호 ex biSeq:1" )
        @PathVariable Long biSeq,
        @RequestBody CommentAddVO data) {
        MapVO map = commentService.addComment(member, biSeq, data);
        return new ResponseEntity<MapVO>(map, HttpStatus.OK);
    }

    // 댓글조회
    // 게시글에 일치하는것만 조회 ciStatus가 0정상인 것들만 조회!
    @Operation(summary = "게시판 별 댓글+대댓글 내역",
    description = "댓글 리스트 = ciSeq 댓글번호,nickName 닉네임,ciContent 내용,ciRegDt 등록일,ciEditDt 수정일(수정없으면 null),viewStatus 댓글상태")
    @GetMapping("/list/{biSeq}")
    public ResponseEntity<List<UpperCommentVO>> listComment(
        @Parameter(description = "게시판번호 ex biSeq:1" )
        @PathVariable Long biSeq
        ) {
        return new ResponseEntity<List<UpperCommentVO>>(commentService.commentList(biSeq),HttpStatus.OK);
    }
    
    // 댓글삭제
    // 댓글 ciStatus로 0을 1로 바꿔서 상태로 변경
    @Operation(summary = "댓글삭제", description = "member : 회원번호, ciSeq : 댓글번호")
    @DeleteMapping("/delete/{member}/{ciSeq}")
    public ResponseEntity<MapVO> deleteComment(
        @Parameter(description = "회원번호 ex member:1" )
        @PathVariable Long member,
        @Parameter(description = "댓글번호 ex ciSeq:11" )
        @PathVariable Long ciSeq) {
            MapVO map = commentService.deleteComment(member,ciSeq);
            return new ResponseEntity<MapVO>(map, HttpStatus.OK);
    }

    // 댓글수정
    @Operation(summary = "댓글수정", description = "member : 회원번호, ciSeq : 댓글번호")
    @PostMapping("/update/{member}/{ciSeq}")
    public ResponseEntity<MapVO> updateComment(
        @Parameter(description = "회원번호 ex member:1" )
        @PathVariable Long member,
        @Parameter(description = "댓글번호 ex ciSeq:11" )
        @PathVariable Long ciSeq, 
        @RequestBody CommentUpdateVO data
    ) {
        MapVO map = commentService.updateComment(member, ciSeq, data);
        return new ResponseEntity<MapVO>(map, HttpStatus.ACCEPTED);
    }
    
    
}
