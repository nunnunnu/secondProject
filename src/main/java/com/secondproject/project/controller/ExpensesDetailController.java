package com.secondproject.project.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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
        LocalDate pastFirstDay = null;
        LocalDate pastLastDay = null;

        if(year == null && month == null){ //월 조회
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            pastFirstDay = firstDay.minusMonths(1);
            pastLastDay = LastDay.minusMonths(1);
        }else if(month==null && year!=null){ //년 조회
            firstDay = LocalDate.of(year, 1, 1);
            LastDay = LocalDate.of(year, 12, 31);
            pastFirstDay = firstDay.minusYears(1);
            pastLastDay = LastDay.minusYears(1);
        }else if(year!=null && month!=null){ //이번 달 조회
            firstDay = LocalDate.of(year, month, 1);
            LastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
            pastFirstDay = firstDay.minusMonths(1);
            pastLastDay = LastDay.minusMonths(1);
        }
        MemberInfoEntity member = mRepository.findAll().get(0);
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, LastDay, member);
        DailyExpensesSearchVO pastSearch = new DailyExpensesSearchVO(pastFirstDay, pastLastDay, member);

        Map<String, Object> map = edService.daily(search, pastSearch);

        return new ResponseEntity<>((MonthExpensesResponseVO)map.get("list"),HttpStatus.OK);
    }
    @Operation(summary = "카테고리 조회", description ="월별 카테고리 합계를 조회합니다. "
    +"년도만 입력되면 월간 카테고리조회, 년도와 월이 모두 입력되면 월별 조회. 아무것도 입력되지않으면 이번 달 조회입니다.")
    @GetMapping("/cate")
    public ResponseEntity<List<CategoryExpensesVO>> getCateTotal(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month
    ){

        LocalDate firstDay = null;
        LocalDate LastDay = null;
        

        if(year == null && month == null){ //월간 조회
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }else if(month==null && year!=null){ //년 조회
            firstDay = LocalDate.of(year, 1, 1);
            LastDay = LocalDate.of(year, 12, 31);
        }else if(year!=null && month!=null){ //이번달 조회
            firstDay = LocalDate.of(year, month, 1);
            LastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        }
        MemberInfoEntity member = mRepository.findAll().get(0);
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, LastDay, member);
        
        Map<String, Object> map = edService.cateTotal(search);

        return new ResponseEntity<List<CategoryExpensesVO>>((List<CategoryExpensesVO>)map.get("cate"), (HttpStatus)map.get("code"));
        
    }
}
