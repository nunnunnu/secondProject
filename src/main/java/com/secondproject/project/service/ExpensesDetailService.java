package com.secondproject.project.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpensesDetailService {
    private final MemberInfoRepository mRepo;
    private final ExpensesDetailRepository edRepo;

    public Map<String, Object> daily(DailyExpensesSearchVO search){
        Map<String, Object> map = new LinkedHashMap<>();
        List<DailyExpensesVO> list =edRepo.dailyExpenses(search);
        if(list.size()==0){
            MonthExpensesResponseVO month = new MonthExpensesResponseVO(null, null, null);
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            map.put("list", month);
            return map;
        }
        
        MonthExpensesResponseVO month = new MonthExpensesResponseVO();
        month.setting(list);
        map.put("status", true);
        map.put("message", "조회했습니다.");
        map.put("code", HttpStatus.OK);
        map.put("list", month);
        
        return map;
    }
    public Map<String, Object> cateTotal(DailyExpensesSearchVO search){
        Map<String, Object> map = new LinkedHashMap<>();
        List<CategoryExpensesVO> list =edRepo.CategoryExpenses(search);
        if(list.size()==0){
            MonthExpensesResponseVO month = new MonthExpensesResponseVO(null, null, null);
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            map.put("list", month);
            return map;
        }
        map.put("status", true);
        map.put("message", "조회했습니다.");
        map.put("code", HttpStatus.OK);
        map.put("cate", list);
        
        return map;
    }
}
