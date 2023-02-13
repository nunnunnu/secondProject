package com.secondproject.project.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthExpensesResponseVO {
    private List<DailyExpensesVO> daily = new ArrayList<>();
    private DailyExpensesVO maxDay;
    private DailyExpensesVO minDay;

    public void setting(List<DailyExpensesVO> list){
        daily.addAll(list);
        DailyExpensesVO max = DailyExpensesVO.builder().price(0).build();
        DailyExpensesVO min = DailyExpensesVO.builder().price(Integer.MAX_VALUE).build();

        for(DailyExpensesVO daily : list){
            if(daily.getPrice()>max.getPrice()){
                max = daily;
            }
            if(daily.getPrice()<min.getPrice()){
                min = daily;
            }
        }
        this.maxDay = max;
        this.minDay = min;
        
    }
}
