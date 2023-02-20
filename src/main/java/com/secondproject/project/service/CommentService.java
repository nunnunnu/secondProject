package com.secondproject.project.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CommentInfoEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.BoardInfoRepository;
import com.secondproject.project.repository.CommentInfoRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.vo.CommentAddVO;
import com.secondproject.project.vo.MapVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberInfoRepository miRepo;
    private final CommentInfoRepository comRepo;
    private final BoardInfoRepository bRepo;

    public MapVO addComment(Long miSeq,Long biSeq, CommentAddVO data) {
        MapVO map = new MapVO();
        MemberInfoEntity member = miRepo.findById(miSeq).orElse(null);
        BoardInfoEntity board = bRepo.findById(biSeq).get();

        CommentInfoEntity newComment = CommentInfoEntity.builder()
            .ciSeq(data.getCiSeq())
            .memberInfoEntity(miRepo.findById(data.getMiSeq()).get())
            .boardInfoEntity(bRepo.findById(data.getBiSeq()).get())
            .ciContent(data.getCiContent())
            .ciRegDt(data.getCiRegDt())
            .ciEditDt(data.getCiEditDt())
            .build();
        // 유효성 검사 
        comRepo.save(newComment);
        map.setStatus(true);
        map.setMessage("댓글이 등록되었습니다");
        map.setCode(HttpStatus.OK);
        
        return map;
    }
}
