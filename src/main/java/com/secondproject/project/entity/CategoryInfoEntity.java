package com.secondproject.project.entity;

import com.secondproject.project.vo.CategoryExpensesListVO;

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
@Table(name = "category_info")
@Builder
public class CategoryInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_seq")
    private Long cateSeq;
    
    @Column(name = "cate_name")
    private String cateName;

    public void CategoryInfoEntity(CategoryExpensesListVO vo) {
        this.cateSeq = vo.getCateSeq();
        this.cateName = vo.getCateName();
    }
}
