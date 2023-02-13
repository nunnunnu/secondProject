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
import com.secondproject.project.vo.YearExpensesListVO;
import com.secondproject.project.vo.YearExpensesResponseVO;
import com.secondproject.project.vo.YearExpensesVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpensesDetailService {
    private final MemberInfoRepository mRepo;
    private final ExpensesDetailRepository edRepo;

    public Map<String, Object> daily(DailyExpensesSearchVO search, DailyExpensesSearchVO pastSearch){
        Map<String, Object> map = new LinkedHashMap<>();
        List<DailyExpensesVO> list =edRepo.dailyExpenses(search);
        if(list.size()==0){
            MonthExpensesResponseVO month = new MonthExpensesResponseVO();
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            map.put("list", month);
            return map;
        }
        
        MonthExpensesResponseVO month = new MonthExpensesResponseVO();
        month.setting(list);
        month.changeRate(edRepo.totalSum(pastSearch));
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
            MonthExpensesResponseVO month = new MonthExpensesResponseVO();
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            map.put("list", month);
            return map;
        }
        int total = 0;
        for(CategoryExpensesVO cate : list){
            total += cate.getPrice();
        }
        for(CategoryExpensesVO cate : list){
            cate.countRate(total);
        }
        map.put("status", true);
        map.put("message", "조회했습니다.");
        map.put("code", HttpStatus.OK);
        map.put("cate", list);

        return map;
    }

    public Map<String, Object> yearShow(DailyExpensesSearchVO search, DailyExpensesSearchVO pastSearch){
        Map<String, Object> map = new LinkedHashMap<>();
        List<YearExpensesVO> list = edRepo.yearSum(search);
        List<YearExpensesVO> pastList = edRepo.yearSum(pastSearch);
        YearExpensesListVO result = new YearExpensesListVO();
        result.setName("series1");
        for(int i=1;i<=12;i++){
            Boolean chk = true;
            for(YearExpensesVO y : list){
                if(y.getMonth()==i){
                    chk = false;
                    result.addData(y.getSum());
                }
            }
            if(chk){
                result.addData(0);
            }
            
        }
        YearExpensesListVO result2 = new YearExpensesListVO();
        result2.setName("series2");
        for(int i=1;i<=12;i++){
            Boolean chk = true;
            for(YearExpensesVO y : pastList){
                if(y.getMonth()==i){
                    chk = false;
                    result2.addData(y.getSum());
                }
            }
            if(chk){
                result2.addData(0);
            }
            
        }
        YearExpensesResponseVO finalResult = new YearExpensesResponseVO();
        finalResult.addList(result);
        finalResult.addList(result2);
        map.put("data", finalResult);
        map.put("code", HttpStatus.OK);
        return map;
    }
}
