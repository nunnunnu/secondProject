package com.secondproject.project.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "통계용 DTO")
public class YearExpensesListVO {
    @Schema(description = "이름(series1 - 작년, series2 - 올해)")
    private Integer year;
    @Schema(description = "월별 총합계 리스트(순서대로정렬되어있습니다)")
    private int jan;
    private int feb;
    private int mar;
    private int apr;
    private int may;
    private int jun;
    private int jul;
    private int aug;
    private int sep;
    private int oct;
    private int nov;
    private int dec;

    public YearExpensesListVO(List<YearExpensesVO> list){
        for(int i=1;i<=12;i++){
            // Boolean chk = true;
            for(YearExpensesVO y : list){
                if(y.getMonth()==1){
                    this.setJan(y.getSum());
                }else if(y.getMonth()==2){
                    this.setFeb(y.getSum());
                }else if(y.getMonth()==3){
                    this.setMar(y.getSum());
                }else if(y.getMonth()==4){
                    this.setApr(y.getSum());
                }else if(y.getMonth()==5){
                    this.setMay(y.getSum());
                }else if(y.getMonth()==6){
                    this.setJun(y.getSum());
                }else if(y.getMonth()==7){
                    this.setJul(y.getSum());
                }else if(y.getMonth()==8){
                    this.setAug(y.getSum());
                }else if(y.getMonth()==9){
                    this.setSep(y.getSum());
                }else if(y.getMonth()==10){
                    this.setOct(y.getSum());
                }else if(y.getMonth()==11){
                    this.setNov(y.getSum());
                }else if(y.getMonth()==12){
                    this.setDec(y.getSum());
                }
            }
            
        }
    }

}
