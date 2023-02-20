package com.secondproject.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.CommentInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.BoardInfoRepository;
import com.secondproject.project.repository.CategoryInfoRepository;
import com.secondproject.project.repository.CommentInfoRepository;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.service.ExpensesDetailService;
import com.secondproject.project.vo.MonthListExpensesVO;
import com.secondproject.project.vo.UpperCommentVO;



@SpringBootTest
class hyeonjuTest {
    @Autowired MemberInfoRepository mRepo;
    @Autowired ExpensesDetailRepository edRepo;
    @Autowired CategoryInfoRepository cateRepo;
    @Autowired ExpensesDetailService edService;
    @Autowired CommentInfoRepository comRepo;
    @Autowired BoardInfoRepository bRepo;
    
    @Test
    void 월별지출리스트() {
        Integer year = 2022;
        Integer month = 12;
        MemberInfoEntity member = mRepo.findAll().get(0);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, 31); // controller

        List<ExpensesDetailEntity> expenses = edRepo.findByEdMiSeqAndEdDateBetween(member, start, end);
        List<MonthListExpensesVO> monthExpenses = new ArrayList<>();
        for(ExpensesDetailEntity e : expenses){
            monthExpenses.add(new MonthListExpensesVO(e));
        } // service

        System.out.println(monthExpenses); // return monthExpenses
            
    }

    @Test
    @Transactional
    void 지출입력_등록된회원없음() {
        MemberInfoEntity member = mRepo.findAll().get(0);
        // if(member.getMiSeq()!=null){
        //     fail();
        // }
        CategoryInfoEntity cate = cateRepo.findAll().get(0);
        // CategoryInfoEntity cate2 = cateRepo.findById(1L).orElse(null);
        List<ExpensesDetailEntity> expenses = edRepo.findMemberAndCate(member, cate);
        // System.out.println(expenses);
        ExpensesDetailEntity newExpenses = ExpensesDetailEntity.builder()
            .edMiSeq(member)
            .edAmount(15000)
            .edCateSeq(cateRepo.findById(1L).get())
            .edTitle("임시")
            .edDate(LocalDate.now())
            .build();
        newExpenses = edRepo.save(newExpenses);
        System.out.println(newExpenses);

        // ExpensesDetailEntity findEntity = edRepo.findById(newExpenses.getEdSeq()).orElse(null);
        // System.out.println(findEntity);
        // Assertions.assertThat(findEntity).isEqualTo(newExpenses);

    }

    @Test
    @Transactional
    void 지출수정() {
        MemberInfoEntity member = mRepo.findAll().get(0);
        CategoryInfoEntity cate = cateRepo.findAll().get(0);
        String originTitle = "등록";
        List<ExpensesDetailEntity> expenses = edRepo.findMemberAndCate(member, cate);
        ExpensesDetailEntity newExpenses = ExpensesDetailEntity.builder()
        .edMiSeq(member)
        .edAmount(55000)
        .edCateSeq(cateRepo.findById(1L).get())
        .edTitle(originTitle)
        .edDate(LocalDate.now())
        .build();
        
        newExpenses = edRepo.save(newExpenses);
        newExpenses.updateExpensesDetailEntity("수정", LocalDate.now(), 77000, cate);
        newExpenses = edRepo.save(newExpenses);
        System.out.println(newExpenses);
        Assertions.assertThat(newExpenses.getEdTitle()).isNotEqualTo(originTitle);
    }

    @Test
    @Transactional
    void 댓글달기() {
        MemberInfoEntity member = mRepo.findAll().get(2);
        BoardInfoEntity board = bRepo.findAll().get(1);

        // List<CommentInfoEntity> data = comRepo.findByMemberInfoEntity(member);
        CommentInfoEntity newComment = CommentInfoEntity.builder()
            .memberInfoEntity(member)
            .boardInfoEntity(board)
            .ciContent("댓글내용44")
            .ciRegDt(LocalDateTime.now())
            .build();

        comRepo.save(newComment);
        System.out.println(newComment.getCiContent());
    }

    @Test
    void 댓글조회() {
        BoardInfoEntity board = bRepo.findAll().get(2);

        List<CommentInfoEntity> entity = comRepo.findBoard(board); 
        // 게시글에 일치하는것만 조회
        List<UpperCommentVO> upperComment = new ArrayList<>();
        for(CommentInfoEntity c : entity) {
            UpperCommentVO upperVo = new UpperCommentVO(c);
            List<CommentInfoEntity> commentEntity = comRepo.findByCommentInfoEntityOrderByCiRegDt(c); 
            upperVo.commentVo(commentEntity);
            upperComment.add(upperVo);
            // vo에 엔티티로 받는 생성자는 내가 생성을 해줘야함            
        }
        System.out.println(upperComment);
    }
}
