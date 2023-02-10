package com.secondproject.project;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;

@SpringBootTest
public class jinheeTest {
    
    @Autowired ExpensesDetailRepository edRepo;
    @Autowired MemberInfoRepository memberRepo;

    @Test
    void 테스트(){
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate LastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        List<ExpensesDetailEntity> list = edRepo.findByEdMiSeqAndEdDateBetween(memberRepo.findAll().get(0), firstDay, LastDay);

        for(ExpensesDetailEntity e : list){
            System.out.println(e.getEdTitle());
        }

    }
}
