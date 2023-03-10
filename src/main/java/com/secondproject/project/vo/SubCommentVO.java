package com.secondproject.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

import com.secondproject.project.entity.CommentInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "대댓글")
public class SubCommentVO {
    @Schema(description = "댓글번호")
    private Long ciSeq;
    @Schema(description = "회원닉네임")
    private String nickName;
    @Schema(description = "대댓글내용")
    private String ciContent;
    @Schema(description = "대댓글등록일")
    private LocalDateTime ciRegDt;
    @Schema(description = "댓글수정일")
    private LocalDateTime ciEditDt;
    @Schema(description = "대댓글상태 true 정상 false 삭제된것")
    private Boolean viewStatus;

    public SubCommentVO(CommentInfoEntity c) {
        this.ciSeq = c.getCiSeq();
        this.nickName = c.getMemberInfoEntity().getMiNickname();
        this.ciContent = c.getCiContent();
        this.ciRegDt = c.getCiRegDt();
        this.ciEditDt = c.getCiEditDt();
        if(c.getCiStatus() == 0) {
            this.viewStatus = true;
        }else {
            this.viewStatus = false;
        }
    }
}
