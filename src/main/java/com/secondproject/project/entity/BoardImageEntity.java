package com.secondproject.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="board_image")
public class BoardImageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bimg_seq") 
    private Long bimgSeq;
    
    @Column(name="bimg_name") 
    private String bimgName;

    @Column(name="bimg_uri") 
    private String bimgUri;
    
    @OneToOne(fetch = FetchType.LAZY) @JsonIgnore @Column(name="bimg_bi_seq") 
    private BoardInfoEntity bimgBiSeq;

}

