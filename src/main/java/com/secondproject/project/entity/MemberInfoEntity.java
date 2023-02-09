package com.secondproject.project.entity;

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
@Builder
public class MemberInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mi_seq") 
    private Long miSeq;

    @Column(name = "mi_email") 
    private String miEmail;

    @Column(name = "mi_pwd") 
    private String miPwd;

    @Column(name = "mi_token") 
    private String miToken;

    @Column(name = "mi_sns_type") 
    private String miSnsType;
    
    @Column(name = "mi_target_amount") 
    private Integer miTargetAmount;
}
