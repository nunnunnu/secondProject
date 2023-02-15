package com.secondproject.project.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
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
import com.secondproject.project.service.SearchService;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.MapVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;
import com.secondproject.project.vo.YearExpensesListVO;
import com.secondproject.project.vo.expenses.TargetRateVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
@Tag(description = "지출 통계 관련 API입니다.", name = "통계")
public class ExpensesDetailController {
    private final ExpensesDetailService edService;
    private final MemberInfoRepository mRepository;
    private final SearchService sService;
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회성공", content = @Content(schema = @Schema(implementation = MonthExpensesResponseVO.class))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 or month만 입력", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "204", description = "해당 기간에 등록된 지출내역이 없음.", content = @Content(schema = @Schema(implementation = MapVO.class))) })
    @Operation(summary = "일별지출합계+가장많이쓴날+가장적게쓴날", description ="입력한 달의 일별 지출 합계와 가장 많이쓴날/적게쓴날을 조회합니다. "
    +"입력값이 없다면 현재 월을 조회, 년도만 들어오면 해당 년도 전체조회(일별합계입니다), 년도와 월 모두 들어오면 해당 월을 조회")
    @GetMapping("/month/{seq}")
    public ResponseEntity<Object> getMonth(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month,
        @Parameter(description = "회원번호") @PathVariable Long seq
    ){
        Map<String, Object> map = new LinkedHashMap<>();
        
        DailyExpensesSearchVO search = sService.dateSearch(year, month, seq);
        if(search==null){
            map.put("status", false);
            map.put("message", "회원번호 오류, 또는 월만 입력하셨습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        DailyExpensesSearchVO pastSearch;
        if(Duration.between(search.getStartDay().atStartOfDay(), search.getLastDay().atStartOfDay()).toDays()>31){
            Integer pastYear = search.getStartDay().getYear()-1;
            pastSearch = sService.dateSearch(pastYear, null, seq);
        }else{
            Integer pastMonth = search.getStartDay().getMonthValue()-1<=0?12:search.getStartDay().getMonthValue()-1;
            pastSearch = sService.dateSearch(search.getStartDay().getYear(), pastMonth, seq);
        }
        map = edService.daily(search, pastSearch);
        if(!(boolean)map.get("status")){
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(map.get("list"),HttpStatus.OK);
        }
    }
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(array=@ArraySchema(schema = @Schema(implementation = CategoryExpensesVO.class)))),
        @ApiResponse(responseCode = "204", description = "지출내역없음", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 or month만 입력", content = @Content(schema =@Schema(implementation = MapVO.class))) })
    @Operation(summary = "카테고리 조회", description ="월별 카테고리 합계를 조회합니다. "
    +"년도만 입력되면 월간 카테고리조회, 년도와 월이 모두 입력되면 월별 조회. 아무것도 입력되지않으면 이번 달 조회입니다.")
    @GetMapping("/cate/{seq}")
    public ResponseEntity<Object> getCateTotal(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month,
        @Parameter(description = "회원번호") @PathVariable Long seq
    ){

        Map<String, Object> map = new LinkedHashMap<>();
        DailyExpensesSearchVO search = sService.dateSearch(year, month, seq);
        if(search==null){
            map.put("status", false);
            map.put("message", "회원번호 오류, 또는 월만 입력하셨습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        // MemberInfoEntity member = mRepository.findById(seq).orElse(null);
        
        map = edService.cateTotal(search);
        if(!(boolean)map.get("status")){
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(map.get("cate"),HttpStatus.OK);
        }
        
    }
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(array=@ArraySchema(schema = @Schema(implementation = YearExpensesListVO.class)))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류", content = @Content(array  = @ArraySchema(schema =@Schema(implementation = MapVO.class)))) })
    @Operation(summary = "연간 조회", description ="입력받은 년도의 금액과(null일시 올해) 전 해의 월별 총 합계를 출력합니다.")
    @GetMapping("/year/{seq}")
    public ResponseEntity<Object> getYearChart(
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "회원번호") @PathVariable Long seq
    ){
        Map<String, Object> map = new LinkedHashMap<>();
        
        DailyExpensesSearchVO search = sService.dateSearch(year==null?LocalDate.now().getYear():year, null, seq);
        if(search==null){
            map.put("status", false);
            map.put("message", "회원번호 오류, 또는 월만 입력하셨습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        DailyExpensesSearchVO pastSearch;
        if(Duration.between(search.getStartDay().atStartOfDay(), search.getLastDay().atStartOfDay()).toDays()>31){
            Integer pastYear = search.getStartDay().getYear()-1;
            pastSearch = sService.dateSearch(pastYear, null, seq);
        }else{
            Integer pastMonth = search.getStartDay().getMonthValue()-1<=0?12:search.getStartDay().getMonthValue()-1;
            pastSearch = sService.dateSearch(search.getStartDay().getYear(), pastMonth, seq);
        }
        map = edService.yearShow(search, pastSearch);

        return new ResponseEntity<>(map.get("data"), HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TargetRateVO.class))),
        @ApiResponse(responseCode = "204", description = "지출내역없음", content = @Content(array=@ArraySchema(schema = @Schema(implementation = MapVO.class)))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 or ,month만 입력", content = @Content(array  = @ArraySchema(schema =@Schema(implementation = MapVO.class)))) })
    @Operation(summary = "목표금액 사용비율", description ="입력받은 월의 목표금액 사용률을 조회합니다. year과 month가 null일시 현월을 조회합니다.")
    @GetMapping("/target/{seq}")
    public ResponseEntity<Object> targetRate(
        @PathVariable Long seq,
        @Parameter(description = "조회할 년도(null가능)") @RequestParam @Nullable Integer year,
        @Parameter(description = "조회할 달(null가능)") @RequestParam @Nullable Integer month
    ){
        Map<String, Object> map = new LinkedHashMap<>();
        if(year!=null && month==null){
            map.put("status", false);
            map.put("message", "year과 month를 모두입력하거나 모두 입력해주세요.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        DailyExpensesSearchVO search = sService.dateSearch(year, month, seq);
        if(search==null){
            map.put("status", false);
            map.put("message", "회원번호 오류, 또는 월만 입력하셨습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        
        map = edService.amountRate(search);

        return new ResponseEntity<>(map.get("data"), HttpStatus.OK);
    }
}
