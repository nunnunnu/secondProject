package com.secondproject.project;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.CategoryInfoRepository;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;



@SpringBootTest
class hyeonjuTest {
    @Autowired MemberInfoRepository mRepo;
    @Autowired ExpensesDetailRepository edRepo;
    @Autowired CategoryInfoRepository cateRepo;
    
    @Test
    void 월별지출총금액() {

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
}