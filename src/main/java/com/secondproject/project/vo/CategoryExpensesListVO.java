package com.secondproject.project.vo;

import com.secondproject.project.entity.CategoryInfoEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "카테고리리스트")
public class CategoryExpensesListVO {
    @Schema(description = "카테고리 번호")
    private Long cateSeq;
    // private String cateSeq; <-변경
    @Schema(description = "카테고리 이름")
    private String cateName;
    
    public CategoryExpensesListVO(CategoryInfoEntity cate) {
        // if(cate.getCateSeq()==1){
        //     this.cateSeq = "지출";
        // }else if(cate.getCateSeq()==2){
        //     this.cateSeq = "수입";
        // } <-변경
        this.cateSeq = cate.getCateSeq();
        this.cateName = cate.getCateName();
    }
}
