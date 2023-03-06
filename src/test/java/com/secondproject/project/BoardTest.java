package com.secondproject.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CommentLikesEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.entity.TargetAreaInfoEntity;
import com.secondproject.project.repository.BoardInfoRepository;
import com.secondproject.project.repository.CommentLikesRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;

@SpringBootTest
@Transactional
public class BoardTest {

    private MemberInfoEntity member;
    private BoardInfoEntity newBoard;
    private TargetAreaInfoEntity target;

    @Autowired MemberInfoRepository mRepo;
    @Autowired BoardInfoRepository bRepo;
    @Autowired TargerAreaInfoRepository tRepo;
    @Autowired CommentLikesRepository clRepo;
    
    @BeforeEach
    public void beforeEach(){
        member = new MemberInfoEntity(null, "user0000999@mdmdm.cidmn", "123456", null, null, 60000, "미미", 0, 1);
        mRepo.save(member);

        target = tRepo.findTarget(member.getMiTargetAmount());
        
        newBoard = new BoardInfoEntity(null, member, "@@@테스트글제목", "글내용", LocalDateTime.now(), null, 0, 0, target, null, null);
        bRepo.save(newBoard);

    }

    @Test
    public void 게시글등록(){
        BoardInfoEntity board = new BoardInfoEntity(null, member, "글제목1", "글내용1", LocalDateTime.now(), null, 0, 0, target, null, null);
        bRepo.save(board);

        BoardInfoEntity findBoard = bRepo.findByBiSeqAndBiStatus(board.getBiSeq(),0);
        
        Assertions.assertThat(board.getBiSeq()).isEqualTo(findBoard.getBiSeq());
        
    }
    @Test
    public void 게시글수정(){
        String originTitle = newBoard.getBiTitle();
        String originContant = newBoard.getBiDetail();
        newBoard.setBiTitle("수정함");
        newBoard.setBiDetail("글내용수정");
        newBoard.setBiEditDt(LocalDateTime.now());
        bRepo.save(newBoard);

        assertThat(newBoard.getBiTitle()).isNotEqualTo(originTitle);
        assertThat(newBoard.getComment()).isNotEqualTo(originContant);
        assertThat(newBoard.getBiEditDt()).isNotNull();
    }
    @Test
    public void 게시글삭제(){
        BoardInfoEntity findBoard = bRepo.findByBiSeqAndBiStatus(newBoard.getBiSeq(),0);
        bRepo.delete(findBoard);
        
        BoardInfoEntity result = bRepo.findByBiSeqAndBiStatus(newBoard.getBiSeq(),0);
        
        assertThat(result).isNull();
    }
    @Test
    public void 게시글삭제실패(){
        BoardInfoEntity board = new BoardInfoEntity(null, member, "글제목1", "글내용1", LocalDateTime.now(), null, 0, 1, target, null, null);
        bRepo.save(board);
        if(board.getBiStatus()==0){
            fail();
            bRepo.delete(board);
        }
    }
    @Test
    public void 게시글조회(){
        Integer view = newBoard.getBiViews();
        BoardInfoEntity findBoard = bRepo.findByBiSeqAndBiStatus(newBoard.getBiSeq(),0);
        findBoard.upView();
        bRepo.save(findBoard);
        
        assertThat(findBoard.getBiSeq()).isEqualTo(newBoard.getBiSeq());
        //조회수 증가
        assertThat(findBoard.getBiViews()).isEqualTo(view+1);
        
    }
    @Test
    public void 게시글좋아요(){
        Long originLike = clRepo.countByClStatusAndClBiSeq(0, newBoard);
        CommentLikesEntity entity = new CommentLikesEntity(null, member, newBoard, 0);
        clRepo.save(entity);
        Long afterLike = clRepo.countByClStatusAndClBiSeq(0, newBoard);
        
        assertThat(originLike).isNotEqualTo(afterLike);
    }
    @Test
    public void 게시글좋아요실패(){
        CommentLikesEntity entity = new CommentLikesEntity(null, member, newBoard, 0);
        clRepo.save(entity);
        if(clRepo.countByClStatusAndClBiSeq(0, newBoard)==0){
            fail();
            CommentLikesEntity testEntity = new CommentLikesEntity(null, member, newBoard, 0);
            clRepo.save(testEntity);
        }
    }
    @Test
    public void 게시글검색(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<BoardInfoEntity> boards = bRepo.findByBiTitleContainsAndBiStatus("@@@테스트", pageable,0);

        assertThat(boards.getContent().size()).isEqualTo(1);

        
    }

}
