package com.secondproject.project.vo;

import java.time.LocalDate;

import com.secondproject.project.entity.CommentInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAddVO {
    private Long ciSeq;
    private Long miSeq;
    private String ciContent;
    private LocalDate ciRegDt;
    private LocalDate ciEditDt;
    private Long ciCiseq; // 답댓글생각해서
    private Long biSeq; 

    public CommentAddVO(CommentInfoEntity entity) {
        this.ciSeq = entity.getCiSeq();
        this.miSeq = entity.getMemberInfoEntity().getMiSeq();
        this.ciContent = entity.getCiContent();
        this.ciRegDt = entity.getCiRegDt();
        this.ciEditDt = entity.getCiEditDt();
        this.ciCiseq = entity.getCommentInfoEntity().getCiSeq();
        this.biSeq = entity.getBoardInfoEntity().getBiSeq();
    }
}
