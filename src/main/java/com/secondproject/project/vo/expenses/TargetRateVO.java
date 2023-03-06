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
@Schema(description = "목표금액 사용률 조회")
public class TargetRateVO {
    @Schema(description = "목표금액")
    private Integer target;
    @Schema(description = "사용금액")
    private Integer used;
    @Schema(description = "남은금액")
    private Integer remaining;
    @Schema(description = "사용한 비율")
    private Double usedRate;
    @Schema(description = "남은 비율")
    private Double remainingRete;

    public TargetRateVO(Integer target, Integer used){
        this.target = target;
        this.used = used;
        this.remaining = target - used;
        this.usedRate = (((double)used / (double)target)) * 100;
        this.remainingRete = 100-usedRate;
    }
}
