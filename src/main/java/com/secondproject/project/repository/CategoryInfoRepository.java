package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.CategoryInfoEntity;

public interface CategoryInfoRepository extends JpaRepository<CategoryInfoEntity, Long>{
    
}
