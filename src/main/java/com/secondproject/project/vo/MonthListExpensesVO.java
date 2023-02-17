package com.secondproject.project.vo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthListExpensesVO {
    private Long edMiSeq;
    private Long edSeq;
    private String edTitle;
    private Long edCateSeq;
    private String edCateName;
    private LocalDate edDate;
    private Integer edAmount;

    // 월별 사용내역 리스트 뽑으려고 만들어둔 VO생성자
    public MonthListExpensesVO(ExpensesDetailEntity expenses) {
        this.edMiSeq = expenses.getEdMiSeq().getMiSeq();
        this.edSeq = expenses.getEdSeq();
        this.edTitle = expenses.getEdTitle();
        this.edCateSeq = expenses.getEdCateSeq().getCateSeq();
        this.edCateName = expenses.getEdCateSeq().getCateName();
        this.edDate = expenses.getEdDate();
        this.edAmount = expenses.getEdAmount();
    }
}
