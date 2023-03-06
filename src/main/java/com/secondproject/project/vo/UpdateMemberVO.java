package com.secondproject.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 정보 수정 DTO")
public class UpdateMemberVO {
    @Schema(description = "기존 비밀번호")
    private String miPwd;
    @Schema(description = "수정할 비밀번호")
    private String miUpdatePwd;
    @Schema(description = "수정할 비밀번호 확인")
    private String miCheckUpdatePwd;
    @Schema(description = "수정할 닉네임")
    private String miNickname;
    @Schema(description = "수정할 목표금액")
    private Integer miTargetAmount;
    // private Integer miStatus;
    // private String miEmail;
}
