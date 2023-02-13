package com.secondproject.project.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "월간 사용내역 조회(일별 합계)")
public class MonthExpensesResponseVO {
    @Schema(description ="일별 합계")
    private List<DailyExpensesVO> daily = new ArrayList<>();
    @Schema(description ="가장 많이 쓴 날")
    private DailyExpensesVO maxDay;
    @Schema(description ="가장 적게 쓴 날")
    private DailyExpensesVO minDay;
    @Schema(description ="월 사용금액 합계")
    private Integer total;
    @Schema(description ="증감률(반올림 필요하면 말씀해주세요), 음수 - 전 지출 대비 감소, 양수 - 전 지출 대비 증가, null - 전 사용금액 없음")
    private Double rate;

    public void setting(List<DailyExpensesVO> list){
        System.out.println(rate);
        daily.addAll(list);
        DailyExpensesVO max = DailyExpensesVO.builder().price(0).build();
        DailyExpensesVO min = DailyExpensesVO.builder().price(Integer.MAX_VALUE).build();
        this.total = 0;
        for(DailyExpensesVO daily : list){
            if(daily.getPrice()>max.getPrice()){
                max = daily;
            }
            if(daily.getPrice()<min.getPrice()){
                min = daily;
            }
            total += daily.getPrice();
        }
        this.maxDay = max;
        this.minDay = min;
        
    }

    public void changeRate(Integer lastTotal){
        if(lastTotal!=null){
            this.rate = (((double)(total - lastTotal)/ lastTotal))*100;
        }
    }
}
