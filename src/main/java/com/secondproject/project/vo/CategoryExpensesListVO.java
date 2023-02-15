package com.secondproject.project.vo;

import com.secondproject.project.entity.CategoryInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryExpensesListVO {
    private Long cateSeq;
    private String cateName;
    
    public CategoryExpensesListVO(CategoryInfoEntity cate) {
        this.cateSeq = cate.getCateSeq();
        this.cateName = cate.getCateName();
    }
}
