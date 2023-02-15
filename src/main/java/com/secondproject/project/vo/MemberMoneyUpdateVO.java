package com.secondproject.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "목표금액 수정 DTO")
public class MemberMoneyUpdateVO {
    @Schema(description = "수정할 목표금액")
    private Integer miTargetAmount;
}
