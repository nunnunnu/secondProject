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
@Table(name = "target_area_info")
@Builder
public class TargetAreaInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tai_seq")
    private Long taiSeq;

    @Column(name = "tai_name")
    private String taiName;

    @Column(name = "tai_min_cost")
    private Integer taiMinCost;
    
    @Column(name = "tai_max_cost")
    private Integer taiMaxCost;
}
