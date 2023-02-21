package com.secondproject.project.vo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.secondproject.project.entity.CommentInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "댓글")
public class UpperCommentVO {
    @Schema(description = "댓글번호")
    private Long ciSeq;
    @Schema(description = "회원닉네임")
    private String nickName;
    @Schema(description = "댓글내용")
    private String ciContent;
    @Schema(description = "댓글등록일")
    private LocalDateTime ciRegDt;
    @Schema(description = "댓글상태 true 정상 false 삭제된것")
    private Boolean viewStatus;
    @Schema(description = "대댓글")
    private List<SubCommentVO> subCommentVO = new ArrayList<>();

    public void commentVo( List <CommentInfoEntity> entity) {
        // List <SubCommentVO> comment = new ArrayList<>();
        for(CommentInfoEntity c : entity) {
            subCommentVO.add(new SubCommentVO(c));
        }
    }

	public UpperCommentVO(CommentInfoEntity c) {
        this.ciSeq = c.getCiSeq();
        this.nickName = c.getMemberInfoEntity().getMiNickname();
        this.ciContent = c.getCiContent();
        this.ciRegDt = c.getCiRegDt();
        if(c.getCiStatus() == 0) {
            this.viewStatus = true;
        }else {
            this.viewStatus = false;
        }
	}
}
