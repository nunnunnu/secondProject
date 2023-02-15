package com.secondproject.project.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlusMinusExpensesVO {
    // 지출내역 추가, 수정을 위해서
    private Long edSeq;
    // private Long miSeq; // 번호는 시퀀스로 입력시킴
    private String edTitle;
    private Long edCateSeq;
    private LocalDate edDate;
    private Integer edAmount;

}
