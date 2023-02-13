package com.secondproject.project.vo;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "일별 합계")
public class DailyExpensesVO {
    @Schema(description = "날짜")
    private LocalDate date;
    @Schema(description = "사용합계")
    private Integer price;
}
