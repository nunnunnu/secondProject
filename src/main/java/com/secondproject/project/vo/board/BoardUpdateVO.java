package com.secondproject.project.vo.board;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "수정할 글 내용")
public class BoardUpdateVO {
    @Schema(description = "수정할 글 제목")
    private String title;
    @Schema(description = "수정할 본문 내용")
    private String detail;
    @Schema(description = "삭제할 이미지 번호")
    private List<Long> imgSeq = new ArrayList<>();
}
