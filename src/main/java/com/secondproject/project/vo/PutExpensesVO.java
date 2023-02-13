package com.secondproject.project.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutExpensesVO {
    //제목, 카테고리번호, 작성날짜, 금액 입력 -all not null
    private String edtitle;
    private Long cateSeq;
    private LocalDate edDate;
    private Integer edAmont;



}
