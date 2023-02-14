package com.secondproject.project.vo;

import lombok.Data;

@Data
public class UpdateMemberVO {
    private String miUpdatePwd;
    private String miCheckUpdatePwd;
    private String miPwd;
    private String miNickname;
    private Integer miTargetAmount;
    private Integer miStatus;
    private String miEmail;
}
