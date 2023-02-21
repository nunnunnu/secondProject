package com.secondproject.project.vo;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

import com.secondproject.project.entity.CommentInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "댓글 등록")
public class CommentAddVO {
    // json raw 형태로 보내는 부분만 들어가면 되욤
    // private Long ciSeq;
    // private Long miSeq; -> controller에 pathvariable이 없어도 됨
    // private String miNickname; // 댓글 닉네임 등록
    @Schema(description = "댓글 내용")
    private String ciContent;
    // private LocalDate ciRegDt;
    // private LocalDate ciEditDt;
    @Schema(description = "댓글의 번호(답댓글용) 일반댓글은 null 로 넣으시면 됩니다.")
    private Long ciCiseq; // 답댓글생각해서
    // private Long biSeq;  //-> controller에 pathvariable이 없어도 됨

    public CommentAddVO(CommentInfoEntity entity) {
        // this.ciSeq = entity.getCiSeq();
        // this.miSeq = entity.getMemberInfoEntity().getMiSeq();
        // this.miNickname = entity.getMemberInfoEntity().getMiNickname(); // 댓글 닉네임 등록
        this.ciContent = entity.getCiContent();
        // this.ciRegDt = entity.getCiRegDt();
        // this.ciEditDt = entity.getCiEditDt();
        // this.ciCiseq = entity.getCommentInfoEntity().getCiSeq();
        // this.biSeq = entity.getBoardInfoEntity().getBiSeq();
    }
}
