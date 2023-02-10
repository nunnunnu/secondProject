package com.secondproject.project.vo;

import java.time.LocalDate;

import com.secondproject.project.entity.MemberInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyExpensesSearchVO {
    private LocalDate startDay;
    private LocalDate lastDay;
    private MemberInfoEntity member;
}
