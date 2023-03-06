package com.secondproject.project.vo.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 입력 DTO")
public class BoardinsertVO {
    @Schema(description = "글 제목")
    private String title;
    @Schema(description = "글 내용")
    private String detail;
}
