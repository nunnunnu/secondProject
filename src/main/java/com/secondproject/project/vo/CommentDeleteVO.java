package com.secondproject.project.vo;

import com.secondproject.project.entity.CommentInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDeleteVO {
    private Integer ciStatus;

    public CommentDeleteVO(CommentInfoEntity entity) {
        this.ciStatus = entity.getCiStatus();
    }
}
