package com.secondproject.project.vo;

import java.time.LocalDate;

import com.secondproject.project.entity.ExpensesDetailEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpensesDetailListVO {
    private Long miSeq; // 해당회원 MemberInfoEntity ??
    private String miNickName; // 닉네임 MemberInfoEntity
    private String miEmail; // 닉네임 MemberInfoEntity
    private LocalDate edMonthDate; // 지출날짜 - 월별로 edDate
    private Integer edTotalAmount; // 지출내역 - 총금액 edAmount

}

