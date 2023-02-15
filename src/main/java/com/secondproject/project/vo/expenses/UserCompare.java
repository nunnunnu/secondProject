package com.secondproject.project.vo.expenses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCompare {
    private Integer userAvg;
    private Long seq;
    private String cate;
    private Integer myAvg;
}
