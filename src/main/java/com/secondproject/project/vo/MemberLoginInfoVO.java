package com.secondproject.project.vo;

import com.secondproject.project.entity.MemberInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "로그인 회원정보 DTO")
public class MemberLoginInfoVO {
    @Schema(description = "회원 번호")
    private Long miSeq;
    @Schema(description = "회원 이메일")
    private String miEmail;
    @Schema(description = "회원 가입 타입")
    private String miSnsType;
    @Schema(description = "회원 목표금액")
    private Integer miTargetAmount;
    @Schema(description = "회원 상태(1:정상 / 2:정지)")
    private Integer miStatus;
    @Schema(description = "회원 닉네임")
    private String miNickname;
    @Schema(description = "회원 성별(0:선택안함 / 1:남 / 2:여)")
    private Integer miGen;

    public MemberLoginInfoVO(MemberInfoEntity data) {
        this.miSeq = data.getMiSeq();
        this.miEmail = data.getMiEmail();
        this.miSnsType = data.getMiSnsType();
        this.miTargetAmount = data.getMiTargetAmount();
        this.miStatus = data.getMiStatus();
        this.miNickname = data.getMiNickname();
        this.miGen = data.getMiGen();
    }
}
