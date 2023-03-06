package com.secondproject.project.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Service;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.vo.DailyExpensesSearchVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final MemberInfoRepository mRepository;
    
    public DailyExpensesSearchVO dateSearch(Integer year, Integer month, Long seq){
        LocalDate firstDay = null;
        LocalDate lastDay = null;
        if(year == null && month == null){ //월 조회
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }else if(month==null && year!=null){ //년 조회
            firstDay = LocalDate.of(year, 1, 1);
            lastDay = LocalDate.of(year, 12, 31);
        }else if(year!=null && month!=null){ //이번 달 조회
            firstDay = LocalDate.of(year, month, 1);
            lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        }else{
            return null;
        }
        MemberInfoEntity member = mRepository.findById(seq).orElse(null);
        if(member==null){
            return null;
    
        }
        return new DailyExpensesSearchVO(firstDay, lastDay, member);
        // DailyExpensesSearchVO pastSearch = new DailyExpensesSearchVO(pastFirstDay, pastlastDay, member);

    }
}
