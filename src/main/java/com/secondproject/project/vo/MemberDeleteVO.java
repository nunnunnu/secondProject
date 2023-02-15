package com.secondproject.project.vo;

import com.secondproject.project.entity.MemberInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원탈퇴 DTO")
public class MemberDeleteVO {
    @Schema(description = "회원 상태값")
    private Integer miStatus;
    
    public MemberDeleteVO(MemberInfoEntity entity) {
        this.miStatus = entity.getMiStatus();
    }
}
