package com.secondproject.project.vo.board;

import java.time.LocalDate;

import com.secondproject.project.entity.BoardInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "게시글 조회")
public class BoardShowVO {
    @Schema(description = "번호")
    private Long seq;
    @Schema(description = "글제목")
    private String title;
    @Schema(description = "글 작성일")
    private LocalDate regDt;
    @Schema(description = "댓글 수")
    private int comment;
    @Schema(description = "좋아요 수")
    private Long likes;

    public BoardShowVO(BoardInfoEntity entity, Long likes){
        this.seq = entity.getBiSeq();
        this.title = entity.getBiTitle();
        this.regDt = entity.getBiRegDt().toLocalDate();
        this.comment = entity.getComment().size();
        this.likes = likes;
    }

}
