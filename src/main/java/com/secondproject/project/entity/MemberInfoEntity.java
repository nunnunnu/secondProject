package com.secondproject.project.entity;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondproject.project.vo.MemberAddVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_info")
@DynamicInsert
@Builder
public class MemberInfoEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mi_seq") 
    @JsonIgnore
    private Long miSeq;
    
    @Column(name = "mi_email") 
    private String miEmail;
    
    @Column(name = "mi_pwd") 
    @JsonIgnore
    private String miPwd;
    
    @Column(name = "mi_token")
    @JsonIgnore
    private String miToken;
    
    @Column(name = "mi_sns_type") 
    private String miSnsType;
    
    @Column(name = "mi_target_amount") 
    private Integer miTargetAmount;
    
    @Column(name = "mi_nickname") 
    private String miNickname;
    
    @Column(name = "mi_gen") 
    private Integer miGen;

    @Column(name = "mi_status")
    private Integer miStatus;
    
    public MemberInfoEntity(MemberAddVO data) {
        this.miEmail = data.getMiEmail();
        this.miPwd = data.getMiPwd();
        this.miNickname = data.getMiNickname();
        this.miGen = data.getMiGen();
        this.miTargetAmount = data.getMiTargetAmount();
        this.miToken = data.getMiToken();
        this.miSnsType = data.getMiSnsType();
    }
}
