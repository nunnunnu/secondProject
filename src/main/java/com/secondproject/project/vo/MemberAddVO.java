package com.secondproject.project.vo;

import lombok.Data;

@Data
public class MemberAddVO {
    private Long miSeq;
    private String miEmail;
    private String miPwd;
    private String miToken;
    private String miSnsType;
    private Integer miTargetAmount;
    private Integer miStatus;
    private String miNickname;
    private Integer miGen;
    private String miCheckPwd;
}
