package com.secondproject.project.vo.board;

import java.time.LocalDate;

import com.secondproject.project.entity.BoardInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardShowVO {
    private Long seq;
    private String title;
    private LocalDate regDt;
    private int comment;
    private Long likes;

    public BoardShowVO(BoardInfoEntity entity, Long likes){
        this.seq = entity.getBiSeq();
        this.title = entity.getBiTitle();
        this.regDt = entity.getBiRegDt().toLocalDate();
        this.comment = entity.getComment().size();
        this.likes = likes;
    }

}
