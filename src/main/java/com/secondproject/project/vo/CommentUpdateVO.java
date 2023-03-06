package com.secondproject.project.vo;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class CommentUpdateVO { 
    // 내용, 수정날짜
    private String ciContent;

}
