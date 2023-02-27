package com.secondproject.project.vo;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지출입력VO")
public class PutExpensesVO {
    @Schema(description = "지출내용")
    private String edtitle;
    @Schema(description = "카테고리번호")
    private Long cateSeq;
    @Schema(description = "날짜")
    private LocalDate edDate;
    @Schema(description = "금액")
    private Integer edAmont;
    @Schema(description = "지출수단번호 0이면 강제 1입력")
    private Long piSeq;
    private Integer sat;

}
