package com.secondproject.project.vo;

import com.secondproject.project.entity.PaymentInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "결제수단리스트")
public class PaymentListVO {
    @Schema(description = "결제수단 번호")
    private Long piSeq;
    @Schema(description = "결제수단 이름")
    private String piName;

    public PaymentListVO(PaymentInfoEntity pay) {
        this.piSeq = pay.getPiSeq();
        this.piName = pay.getPiName();
    }
}
