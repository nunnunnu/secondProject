package com.secondproject.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.repository.CategoryInfoRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.service.ExpensesDetailService;
import com.secondproject.project.vo.CategoryExpensesListVO;
import com.secondproject.project.vo.MapVO;
import com.secondproject.project.vo.MonthListExpensesVO;
import com.secondproject.project.vo.PlusMinusExpensesVO;
import com.secondproject.project.vo.PutExpensesVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "지출 관리", description = "지출 CRUD API")
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesAPIController {
    private final ExpensesDetailService edService;
    private final MemberInfoRepository mRepository;
    private final CategoryInfoRepository cateRepo;

    // 지출내역 카테고리선택 내역 조회 - 검색
    // @Operation(summary = "지출내역-카테고리", description = "카테고리 리스트")
    // @GetMapping("/categoryListSearch")
    // public ResponseEntity<CategoryExpensesListVO> getCategoryExpensesList(
    //     @Parameter(description = "카테고리" )
    //     @RequestParam @Nullable String keyword
    //     ) {
    //     return new ResponseEntity<>(edService.getCategoryExpensesListSearchService(keyword),HttpStatus.OK);
    // }

    // 지출내역 카테고리선택 내역 조회
    @Operation(summary = "지출내역-카테고리", description = "카테고리 리스트 : Try it Out")
    @GetMapping("/categoryList")
    public ResponseEntity<List<CategoryExpensesListVO>> getCateList() {
        return new ResponseEntity<List<CategoryExpensesListVO>>(edService.cateExpensesList(),HttpStatus.OK);
    }

    // 지출내역 조회 (1차 회원의 한달단위 지출 리스트 Get/ 2차 최근 소비내역 3개만 나오게 FINDTOP)
    // @GetMapping("/monthList/{seq}")
    // public ResponseEntity<List<MonthListExpensesVO>> getMonthExpensesList() {
    //     return new ResponseEntity<List<MonthListExpensesVO>>(edService.MonthExpensesList(miSeq, month), HttpStatus.OK);
    // }

    // 지출 입력
    @Operation(summary = "지출내역 입력", description = "edtitle(제목) : 제목, cateSeq(카테고리번호) : 1, edDate(작성날짜) : 2023-02-15 or null(현재날짜저장), edAmont(금액) : 15000")
    @PutMapping("/insert/{member}")
    public ResponseEntity<MapVO> putExpenses(
        @Parameter(description = "회원번호 ex member:1" )
        @PathVariable Long member,
        @RequestBody PutExpensesVO data) {
        MapVO map = edService.putExpensesService(member, data);
        return new ResponseEntity<MapVO>(map, HttpStatus.OK);
    }


    // 지출 수정 (제목/ 카테고리/ 날짜? null 이면 entity에 dynamic 걸려있어서 시간으로 적어줌/ 금액)
    @Operation(summary = "지출내역 수정", description = "edSeq(지출내역 번호) : 1, edtitle(제목) : 제목, cateSeq(카테고리번호) : 1, edDate(작성날짜) : 2023-02-15 or null(현재날짜저장), edAmont(금액) : 15000")
    @PostMapping("/insert/{member}")
    public ResponseEntity<MapVO> updateExpenses(
        @Parameter(description = "회원번호 ex member:1" )
        @PathVariable Long member,
        @RequestBody PlusMinusExpensesVO data) {
        MapVO map = edService.updateExpensesService(member, data);
        return new ResponseEntity<MapVO>(map, HttpStatus.ACCEPTED);
    }

    // 지출 삭제
    @Operation(summary = "지출내역 삭제", description = "지출내역 삭제됩니다.")
    @DeleteMapping("/delete/{member}/{edSeq}")
    public ResponseEntity<MapVO> deleteExpenses(
        @Parameter(description = "회원번호 ex member:1" )
        @PathVariable Long member,
        @Parameter(description = "지출내역 번호 ex edSeq:359" )
        @PathVariable Long edSeq){
        MapVO map = edService.deleteExpensesService(member, edSeq);
        return new ResponseEntity<MapVO>(map, HttpStatus.OK);
    }
}
