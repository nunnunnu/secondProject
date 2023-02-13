package com.secondproject.project.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.service.ExpensesDetailService;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;
import com.secondproject.project.vo.YearExpensesResponseVO;

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

    @Operation(summary = "일별지출합계+가장많이쓴날+가장적게쓴날", description ="입력한 달의 일별 지출 합계와 가장 많이쓴날/적게쓴날을 조회합니다. "
    +"입력값이 없다면 현재 월을 조회, 년도만 들어오면 해당 년도 전체조회(일별합계입니다), 년도와 월 모두 들어오면 해당 월을 조회")
    @GetMapping("/month/{seq}")
    public ResponseEntity<MonthExpensesResponseVO> getMonth(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month,
        @Parameter(description = "회원번호") @PathVariable Long seq
    ){
        LocalDate firstDay = null;
        LocalDate lastDay = null;
        LocalDate pastFirstDay = null;
        LocalDate pastlastDay = null;

        if(year == null && month == null){ //월 조회
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            pastFirstDay = firstDay.minusMonths(1);
            pastlastDay = lastDay.minusMonths(1);
        }else if(month==null && year!=null){ //년 조회
            firstDay = LocalDate.of(year, 1, 1);
            lastDay = LocalDate.of(year, 12, 31);
            pastFirstDay = firstDay.minusYears(1);
            pastlastDay = lastDay.minusYears(1);
        }else if(year!=null && month!=null){ //이번 달 조회
            firstDay = LocalDate.of(year, month, 1);
            lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
            pastFirstDay = firstDay.minusMonths(1);
            pastlastDay = lastDay.minusMonths(1);
        }
        MemberInfoEntity member = mRepository.findById(seq).orElse(null);
        if(member==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

        }
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, lastDay, member);
        DailyExpensesSearchVO pastSearch = new DailyExpensesSearchVO(pastFirstDay, pastlastDay, member);

        Map<String, Object> map = edService.daily(search, pastSearch);

        return new ResponseEntity<>((MonthExpensesResponseVO)map.get("list"),HttpStatus.OK);
    }
    @Operation(summary = "카테고리 조회", description ="월별 카테고리 합계를 조회합니다. "
    +"년도만 입력되면 월간 카테고리조회, 년도와 월이 모두 입력되면 월별 조회. 아무것도 입력되지않으면 이번 달 조회입니다.")
    @GetMapping("/cate/{seq}")
    public ResponseEntity<List<CategoryExpensesVO>> getCateTotal(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month,
        @Parameter(description = "회원번호") @PathVariable Long seq
    ){

        LocalDate firstDay = null;
        LocalDate lastDay = null;
        

        if(year == null && month == null){ //월간 조회
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }else if(month==null && year!=null){ //년 조회
            firstDay = LocalDate.of(year, 1, 1);
            lastDay = LocalDate.of(year, 12, 31);
        }else if(year!=null && month!=null){ //이번달 조회
            firstDay = LocalDate.of(year, month, 1);
            lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        }
        MemberInfoEntity member = mRepository.findById(seq).orElse(null);
        if(member==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, lastDay, member);
        
        Map<String, Object> map = edService.cateTotal(search);

        return new ResponseEntity<List<CategoryExpensesVO>>((List<CategoryExpensesVO>)map.get("cate"), (HttpStatus)map.get("code"));
        
    }
    @Operation(summary = "연간 조회", description ="입력받은 년도의 금액과(null일시 올해) 전 해의 월별 총 합계를 출력합니다.")
    @GetMapping("/year/{seq}")
    public ResponseEntity<YearExpensesResponseVO> getYearChart(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "회원번호") @PathVariable Long seq
    ){
        LocalDate firstDay = null;
        LocalDate lastDay = null;
        LocalDate pastFirstDay = null;
        LocalDate pastLastDay = null;
        
        if(year == null){
            firstDay = LocalDate.of(LocalDate.now().getYear(), 1,1);
            lastDay = LocalDate.of(LocalDate.now().getYear(), 12,12);
            pastFirstDay = firstDay.minusYears(1);
            pastLastDay = lastDay.minusYears(1);
        }else{
            firstDay = LocalDate.of(year, 1,1);
            lastDay = LocalDate.of(year, 12,12);
            pastFirstDay = firstDay.minusYears(1);
            pastLastDay = lastDay.minusYears(1);
        }
        MemberInfoEntity member = mRepository.findById(seq).orElse(null);
        if(member==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(firstDay, lastDay, member);
        DailyExpensesSearchVO pastSearch = new DailyExpensesSearchVO(pastFirstDay, pastLastDay, member);

        Map<String, Object> map = edService.yearShow(search, pastSearch);

        return new ResponseEntity<YearExpensesResponseVO>((YearExpensesResponseVO)map.get("data"), (HttpStatus)map.get("code"));
    }
}
