package com.secondproject.project.vo;

import java.time.LocalDate;

import com.secondproject.project.entity.MemberInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyExpensesVO {
    private LocalDate date;
    private Integer price;
    private MemberInfoEntity member;
}
