package com.secondproject.project.vo;

import com.secondproject.project.entity.MemberInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "로그인 DTO")
public class MemberLoginVO {
    @Schema(description = "로그인 할 회원 이메일")
    private String miEmail;
    @Schema(description = "로그인 할 회원 비밀번호")
    private String miPwd;

    public MemberLoginVO(MemberInfoEntity data) {
        this.miEmail = data.getMiEmail();
        this.miPwd = data.getMiPwd();
    }
}


