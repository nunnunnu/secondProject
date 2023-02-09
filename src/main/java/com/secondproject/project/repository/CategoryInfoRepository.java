package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.secondproject.project.entity.CategoryInfoEntity;

@Repository
public interface CategoryInfoRepository extends JpaRepository<CategoryInfoEntity, Long>{
    
}
