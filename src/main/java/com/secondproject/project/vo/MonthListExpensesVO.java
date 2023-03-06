package com.secondproject.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "월별 지출내역 전체조회")
public class MonthListExpensesVO {
    @Schema(description = "회원 번호")
    private Long edMiSeq;
    @Schema(description = "지출 번호")
    private Long edSeq;
    @Schema(description = "지출 내용")
    private String edTitle;
    @Schema(description = "카테고리 번호")
    private Long edCateSeq;
    @Schema(description = "카테고리 이름")
    private String edCateName;
    @Schema(description = "지출 등록날짜")
    private LocalDate edDate;
    @Schema(description = "지출 금액")
    private Integer edAmount;
    @Schema(description = "결제 수단번호")
    private Long edPiSeq;
    @Schema(description = "결제 수단명")
    private String edPiName;

    // 월별 사용내역 리스트 뽑으려고 만들어둔 VO생성자
    public MonthListExpensesVO(ExpensesDetailEntity expenses) {
        this.edMiSeq = expenses.getEdMiSeq().getMiSeq();
        this.edSeq = expenses.getEdSeq();
        this.edTitle = expenses.getEdTitle();
        this.edCateSeq = expenses.getEdCateSeq().getCateSeq();
        this.edCateName = expenses.getEdCateSeq().getCateName();
        this.edDate = expenses.getEdDate();
        this.edAmount = expenses.getEdAmount();
        if(expenses.getEdPiSeq()!=null){
            this.edPiSeq = expenses.getEdPiSeq().getPiSeq();
            this.edPiName = expenses.getEdPiSeq().getPiName();
        }
    }
}
