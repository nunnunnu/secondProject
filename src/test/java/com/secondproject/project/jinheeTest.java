package com.secondproject.project;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.entity.TargetAreaInfoEntity;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;

import jakarta.persistence.EntityManager;
// 
@SpringBootTest
@Transactional
public class jinheeTest {

    @Autowired 
	EntityManager em;
    
    @Autowired ExpensesDetailRepository edRepo;
    @Autowired MemberInfoRepository memberRepo;
    @Autowired TargerAreaInfoRepository tRepo;

    @Test
    void 회원의일간사용금액조회(){
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate LastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        // MemberInfoEntity member = MemberInfoEntity.builder()
        //                             .miEmail("user00001@email.com")
        //                             .miPwd("123456")
        //                             .miNickname("닉네임")
        //                             .miTargetAmount(500000)
        //                             .build();

        // memberRepo.save(member);

        MemberInfoEntity member = memberRepo.findAll().get(0);
                                    

        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, LastDay, member);
        List<DailyExpensesVO> result = edRepo.dailyExpenses(search);
        
        Assertions.assertThat(result.size()).isNotEqualTo(0);
    }

    @Test
    void 목표구간조회(){
        TargetAreaInfoEntity target = tRepo.findTarget(45000);
        System.out.println(target);
    }
}
