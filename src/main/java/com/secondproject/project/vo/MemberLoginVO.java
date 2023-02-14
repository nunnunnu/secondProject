package com.secondproject.project.vo;

import com.secondproject.project.entity.MemberInfoEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginVO {
    private Long miSeq;
    private String miEmail;
    private String miPwd;
    private String miToken;
    private String miSnsType;
    private Integer miTargetAmount;
    private Integer miStatus;
    private String miNickname;
    private Integer miGen;

    public MemberLoginVO(MemberInfoEntity data) {
        this.miSeq = data.getMiSeq();
        this.miEmail = data.getMiEmail();
        this.miPwd = data.getMiPwd();
        this.miToken = data.getMiToken();
        this.miSnsType = data.getMiSnsType();
        this.miTargetAmount = data.getMiTargetAmount();
        this.miStatus = data.getMiStatus();
        this.miNickname = data.getMiNickname();
        this.miGen = data.getMiGen();
    }
}


