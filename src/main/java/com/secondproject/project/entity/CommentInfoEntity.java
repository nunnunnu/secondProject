package com.secondproject.project.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_info")
@Builder
@DynamicInsert
public class CommentInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_seq")
    private Long ciSeq;

    @JoinColumn(name = "ci_mi_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberInfoEntity memberInfoEntity;

    @Column(name = "ci_content")
    private String ciContent;

    @Column(name = "ci_reg_dt")
    private LocalDateTime ciRegDt;

    @Column(name = "ci_edit_dt")
    private LocalDateTime ciEditDt;

    @Column(name = "ci_status")
    private Integer ciStatus;

    @JoinColumn(name = "ci_ci_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private CommentInfoEntity commentInfoEntity;

    @JoinColumn(name = "ci_bi_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardInfoEntity boardInfoEntity;
}
