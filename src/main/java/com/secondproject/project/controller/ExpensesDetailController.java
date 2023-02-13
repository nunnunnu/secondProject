package com.secondproject.project.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.service.ExpensesDetailService;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
@Tag(description = "지출 통계 관련 API입니다.", name = "통계")
public class ExpensesDetailController {
    private final ExpensesDetailService edService;
    private final MemberInfoRepository mRepository;

    @Operation(summary = "일별사용량+가장많이쓴날+가장적게쓴날", description ="입력한 달의 일별 지출 합계와 가장 많이쓴날/적게쓴날을 조회합니다. 입력값이 없다면 현재 월을 조회합니다. ")
    @GetMapping("/month")
    public ResponseEntity<MonthExpensesResponseVO> getMonth(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month
    ){

        LocalDate firstDay = null;
        LocalDate LastDay = null;

        if(year == null|| month == null){
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }else{
            
            firstDay = LocalDate.of(year, month, 1);
            LastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        }
        MemberInfoEntity member = mRepository.findAll().get(0);
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, LastDay, member);

        Map<String, Object> map = edService.daily(search);

        return new ResponseEntity<MonthExpensesResponseVO>((MonthExpensesResponseVO)map.get("list"), (HttpStatus)map.get("code"));
        
    }
    @Operation(summary = "카테고리 조회", description ="월별 카테고리 합계를 조회합니다.")
    @GetMapping("/cate")
    public ResponseEntity<CategoryExpensesVO> getCateTotal(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month
    ){

        LocalDate firstDay = null;
        LocalDate LastDay = null;

        if(year == null|| month == null){
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }else{
            
            firstDay = LocalDate.of(year, month, 1);
            LastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        }
        MemberInfoEntity member = mRepository.findAll().get(0);
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, LastDay, member);

        Map<String, Object> map = edService.cateTotal(search);

        return new ResponseEntity<CategoryExpensesVO>((CategoryExpensesVO)map.get("cate"), (HttpStatus)map.get("code"));
        
    }
}
