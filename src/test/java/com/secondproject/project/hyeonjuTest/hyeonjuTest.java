package com.secondproject.project.hyeonjuTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.vo.ExpensesDetailListVO;


@SpringBootTest
class hyeonjuTest {
    @Autowired ExpensesDetailRepository edRepo;
    @Autowired MemberInfoRepository memberRepo;
    
    @Test
    void 월별지출총금액() {
        System.out.println("Aaa");
        List<ExpensesDetailListVO> list = edRepo.getExpensesMonthList(1L) ;
        System.out.println(list.size());
        System.out.println(list);
    }
}
