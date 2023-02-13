package com.secondproject.project.vo;

import java.time.LocalDate;

import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
public interface ExpensesMonthVO {
    LocalDate getedDate(); // 월별
    Long getedAmount(); // 월별 지출 총계
    MemberInfoEntity getmiSeqInfoEntity();
}


