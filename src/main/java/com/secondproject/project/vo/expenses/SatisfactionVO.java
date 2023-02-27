package com.secondproject.project.vo.expenses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "만족도 조회")
public class SatisfactionVO {
    @Schema(description = "카테고리 이름")
    private String cate;
    @Schema(description = "만족도 평균")
    private Double grade;
}
