package com.secondproject.project.vo.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.secondproject.project.entity.BoardImageEntity;
import com.secondproject.project.entity.BoardInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDetailShowVO {
    private String title;
    private String detail;
    private Integer view;
    private Long likes;
    private Long unLikes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime editDt;
    private List<ImageVO> uri = new ArrayList<>();

    public BoardDetailShowVO(BoardInfoEntity entity){
        this.title = entity.getBiTitle();
        this.detail = entity.getBiDetail();
        this.view = entity.getBiViews();
        this.regDt = entity.getBiRegDt();
        this.editDt = entity.getBiEditDt();
        for(BoardImageEntity u : entity.getImgs()){
            uri.add(new ImageVO(u.getBimgSeq(), u.getBimgName()));
        }
    }

}
