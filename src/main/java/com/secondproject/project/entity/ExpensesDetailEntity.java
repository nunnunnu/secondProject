package com.secondproject.project.entity;

import java.time.LocalDate;

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
@Table(name="expenses_detail")
public class ExpensesDetailEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ed_seq") 
    private Long edSeq;

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="ed_mi_seq") 
    private MemberInfoEntity edMiSeq;

    @Column(name="ed_title") 
    private String edTitle;

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="ed_ci_seq") 
    private CategoryInfoEntity edCiSeq;

    @Column(name="ed_date") 
    private LocalDate edDate;

    @Column(name="ed_amount") 
    private Integer edAmount;

    @Column(name="ed_commnet") 
    private String edCommnet;
}