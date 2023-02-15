package com.secondproject.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원가입 DTO")
public class MemberAddVO {
    @Schema(description = "회원 번호")
    private Long miSeq;
    @Schema(description = "회원 이메일")
    private String miEmail;
    @Schema(description = "회원 비밀번호")
    private String miPwd;
    @Schema(description = "회원 비밀번호 확인")
    private String miCheckPwd;
    @Schema(description = "회원 토큰")
    private String miToken;
    @Schema(description = "회원 가입 타입")
    private String miSnsType;
    @Schema(description = "회원 목표금액")
    private Integer miTargetAmount;
    @Schema(description = "회원 상태")
    private Integer miStatus;
    @Schema(description = "회원 닉네임")
    private String miNickname;
    @Schema(description = "회원 성별")
    private Integer miGen;
}
