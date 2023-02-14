package com.secondproject.project.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "통계용 DTO")
public class YearExpensesListVO {
    @Schema(description = "이름(series1 - 작년, series2 - 올해)")
    private String name;
    @Schema(description = "월별 총합계 리스트(순서대로정렬되어있습니다)")
    private List<Integer> data = new ArrayList<>();

    public void addData(Integer price){
        data.add(price);
    }
}
