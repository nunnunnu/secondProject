package com.secondproject.project.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PlusMinusExpensesVO {
    // 지출내역 추가, 수정을 위해서
    private Long miSeq;
    private String edtitle;
    private Long cateSeq;
    private LocalDate edDate;
    private Integer edAmount;
}
