package com.secondproject.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.secondproject.project.vo.UpperCommentVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberInfoRepository miRepo;
    private final CommentInfoRepository comRepo;
    private final BoardInfoRepository bRepo;

    // 댓글등록
    public MapVO addComment(Long miSeq, Long biSeq, CommentAddVO data) {
        MapVO map = new MapVO();
        MemberInfoEntity member = miRepo.findById(miSeq).orElse(null);
        if(member == null) {
            // miSeq번호가 없거나! 
            map.setMessage("회원번호 오류입니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            map.setStatus(false);
            return map;
        }
        // 유효성 검사 member
        BoardInfoEntity board = bRepo.findById(biSeq).orElse(null);
        // 유효성 검사 board 
        if(board == null)  {
            map.setMessage("등록된 게시글이 없습니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            map.setStatus(false);
            return map;
        }
        // 답댓글
            // ciCiseq ciSeq가 null이 아닌상태여야만 답댓글이 달릴수 있고
            // 받아온 ciSed 번호로 댓글을 찾아와서
            // 만약에 ciCiseq가 null이 아니면 ciCiseq값을 넣어주고 답댓글을 넣어주고
            // null이면 ciSeq 번호로 댓글을 달아준다 

        CommentInfoEntity newComment = CommentInfoEntity.builder()
        // .ciSeq(data.getCiSeq())
        // .memberInfoEntity(miRepo.findById(data.getMiSeq()).get()) 굳이 이렇게 가져올필요가 없다.
        .memberInfoEntity(member) // member엔티티에서 통으로 가져오면됨.
        .boardInfoEntity(board)
        .ciContent(data.getCiContent())
        .ciRegDt(LocalDateTime.now())
        // .ciEditDt(data.getCiEditDt()) 사용자로부터 입력받는게 아니기 때문에
        // .ciEditDt(LocalDateTime.now()) 나우로 받으면 됨 단, 수정기능이 생겼을 때
        .build();
            System.out.println(data.getCiCiseq());
        if(data.getCiCiseq() != null) {
            CommentInfoEntity reply = comRepo.findById(data.getCiCiseq()).orElse(null);
            if(reply==null){
                map.setMessage("답글을 달 댓글번호 오류입니다.");
                map.setCode(HttpStatus.BAD_REQUEST);
                map.setStatus(false);
                return map;
            }
            if(reply.getCommentInfoEntity()==null){
                newComment.setCommentInfoEntity(reply);
            }else{
                newComment.setCommentInfoEntity(reply.getCommentInfoEntity());
            }
        }

        comRepo.save(newComment);            


        map.setStatus(true);
        map.setMessage("댓글이 등록되었습니다");
        map.setCode(HttpStatus.OK);
        
        return map;
    }

    // 댓글내역 조회
    public List<UpperCommentVO> commentList(Long miSeq) {
        List<CommentInfoEntity> entity = comRepo.findAll();
        List<UpperCommentVO> commentList = new ArrayList<>();
        for(CommentInfoEntity c : entity) {
            commentList.add(new UpperCommentVO(c));
            // vo에 엔티티로 받는 생성자는 내가 생성을 해줘야함
        }
        return commentList;
    }
}
