package com.secondproject.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.service.CommentService;
import com.secondproject.project.vo.CommentAddVO;
import com.secondproject.project.vo.MapVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentAPIController {
    private final CommentService commentService;

    // 댓글등록
    @PutMapping("/add/{member}/{boardSeq}")
    public ResponseEntity<MapVO> addCommnet(@PathVariable Long member,@PathVariable Long boardSeq, @RequestBody CommentAddVO data) {
        MapVO map = commentService.addComment(member, boardSeq, data);
        return new ResponseEntity<MapVO>(map, HttpStatus.OK);
    }

    // 댓글조회
    // @GetMapping("/list")

    
    
}
