package com.secondproject.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comment_likes")
public class CommentLikesEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cl_seq") 
    private Long  clSeq;

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="cl_mi_seq") 
    private MemberInfoEntity clMiSeq;

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="cl_bi_seq") 
    private BoardInfoEntity clBiSeq;

    @Column(name="cl_status") 
    private Integer clStatus;

}
