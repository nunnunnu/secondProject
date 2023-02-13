package com.secondproject.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "카테고리별 합계")
public class CategoryExpensesVO {
    // private Integer year;
    @Schema(description = "카테고리 이름")
    private String cate;
    @Schema(description = "카테고리 합계")
    private Integer price;
    @Schema(description = "카테고리별 퍼센트(반올림필요하면 말씀해주세요)")
    private Double rate;

    public void countRate(int total){
        System.out.println(total);
        System.out.println(price);
        System.out.println();
        this.rate = ((double)price / (double)total) * 100;
    }
    
}
