package com.secondproject.project.vo.expenses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회원 별 카테고리 사용 금액 비교")
public class UserCompare {
    @Schema(description = "나의 카테고리 별 사용금액 합계")
    private Integer mySum;
    // private Long seq;
    @Schema(description = "카테고리 이름")
    private String cate;
    @Schema(description = "사용자 카테고리 사용량 평균")
    private Double userAvg;
    @Schema(description = "비율")
    private Double rate;

    public UserCompare(Integer mySum,
        // Long seq,
        String cate,
        Double userAvg
    ){
        this.mySum = mySum;
        // this.seq = seq;
        this.cate = cate;
        this.userAvg = userAvg;
        this.rate = ((double)mySum / (double)userAvg) * 100;
    }
}
