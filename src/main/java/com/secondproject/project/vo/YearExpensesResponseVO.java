package com.secondproject.project.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearExpensesResponseVO {
    private List<YearExpensesListVO> series = new ArrayList<>();;

    public void addList(YearExpensesListVO vo){
        series.add(vo);
    }
}
