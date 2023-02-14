package com.secondproject.project.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="board_info")
@Builder
public class BoardInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bi_seq") 
    private Long biSeq;

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="bi_mi_seq") 
    private MemberInfoEntity biMiSeq;

    @Column(name="bi_title") 
    private String biTitle;

    @Column(name="bi_detail") 
    private String biDetail;

    @Column(name="bi_reg_dt") 
    private LocalDateTime biRegDt;

    @Column(name="bi_edit_dt") 
    private LocalDateTime biEditDt;

    @Column(name="bi_views") 
    private Integer biViews;

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="bi_tai_seq") 
    private TargetAreaInfoEntity biTaiSeq;

    // @OneToMany(mappedBy="")
}
