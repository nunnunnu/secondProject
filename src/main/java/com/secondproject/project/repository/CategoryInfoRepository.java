package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.CategoryInfoEntity;

public interface CategoryInfoRepository extends JpaRepository<CategoryInfoEntity, Long>{
    CategoryInfoEntity findByCateName(String cateName);
    // List<CategoryInfoEntity> findByCateExpensesList(CategoryInfoEntity cate);
    List<CategoryInfoEntity> findAll();
}
