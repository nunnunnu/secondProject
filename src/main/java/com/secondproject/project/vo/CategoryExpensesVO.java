package com.secondproject.project.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryExpensesVO {
    // private Integer year;
    private String cate;
    private Integer price;
}
