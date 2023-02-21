package com.secondproject.project.vo;

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
public class UpperCommentVO {
    private Long ciSeq;
    private String nickName;
    private String ciContent;
    private LocalDateTime ciRegDt;
    private Integer ciStatus;
    private List<SubCommentVO> subCommentVO = new ArrayList<>();

    public void commentVo( List <CommentInfoEntity> entity) {
        // List <SubCommentVO> comment = new ArrayList<>();
        for(CommentInfoEntity c : entity) {
            subCommentVO.add(new SubCommentVO(c.getCiSeq(), c.getMemberInfoEntity().getMiNickname(), c.getCiContent(), c.getCiRegDt()));
        }
    }

	public UpperCommentVO(CommentInfoEntity c) {
        this.ciSeq = c.getCiSeq();
        this.nickName = c.getMemberInfoEntity().getMiNickname();
        this.ciContent = c.getCiContent();
        this.ciRegDt = c.getCiRegDt();
        this.ciStatus = c.getCiStatus();
	}
}
