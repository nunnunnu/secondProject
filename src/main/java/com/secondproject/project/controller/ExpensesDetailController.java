package com.secondproject.project.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.service.ExpensesDetailService;
import com.secondproject.project.vo.DailyExpensesSearchVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpensesDetailController {
    private final ExpensesDetailService edService;
    private final MemberInfoRepository mRepository;

    @GetMapping("/month")
    public ResponseEntity<Object> getMonth(){
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate LastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        MemberInfoEntity member = mRepository.findAll().get(0);
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, LastDay, member);

        Map<String, Object> map = edService.daily(search);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }
}
