package com.secondproject.project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.service.ExpensesDetailService;
import com.secondproject.project.vo.PutExpensesVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesAPIController {
    private final ExpensesDetailService edService;
    private final MemberInfoRepository mRepository;

    // // 지출내역 조회 (1차 월간 사용금액 조회/ 2차 연간 사용금액 조회)

    // 지출 입력
    @PutMapping("/insert/{member}")
    public ResponseEntity<Object> putExpenses(@PathVariable Long member, @RequestBody PutExpensesVO data) {
        Map<String, Object> map = edService.putExpensesService(member, data);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }




    // 지출 수정 (제목/ 카테고리/ 날짜/ 금액)

    // 지출 삭제
}
