package com.secondproject.project.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class YearExpensesListVO {
    private String name;
    private List<Integer> data = new ArrayList<>();

    public void addData(Integer price){
        data.add(price);
    }
}
