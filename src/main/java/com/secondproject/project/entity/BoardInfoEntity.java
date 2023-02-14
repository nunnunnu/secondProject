package com.secondproject.project.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

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
@DynamicInsert
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

    @OneToMany(mappedBy="bimgBiSeq")
    private List<BoardImageEntity> imgs = new ArrayList<>();
    
    @OneToMany(mappedBy="boardInfoEntity")
    private List<CommentInfoEntity> comment = new ArrayList<>();

    // @OneToMany(mappedBy="clBiSeq")
    // private List<CommentLikesEntity> likes = new ArrayList<>();

    public void upView(){
        this.biViews = this.biViews+1;
    }


}
