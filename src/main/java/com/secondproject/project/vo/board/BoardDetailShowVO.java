package com.secondproject.project.vo.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime regDt;
    private LocalDateTime editDt;
    private List<String> uri = new ArrayList<>();

    public BoardDetailShowVO(BoardInfoEntity entity){
        this.title = entity.getBiTitle();
        this.detail = entity.getBiDetail();
        this.view = entity.getBiViews();
        this.regDt = entity.getBiRegDt();
        this.editDt = entity.getBiEditDt();
        for(BoardImageEntity u : entity.getImgs()){
            uri.add(u.getBimgUri());
        }
    }

}
