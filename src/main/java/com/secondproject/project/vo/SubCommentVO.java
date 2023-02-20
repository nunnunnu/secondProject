package com.secondproject.project.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCommentVO {
    private Long ciSeq;
    private String nickName;
    private String ciContent;
    private LocalDateTime ciRegDt;
}
