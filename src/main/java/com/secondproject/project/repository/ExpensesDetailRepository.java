package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.ExpensesDetailEntity;

public interface ExpensesDetailRepository extends JpaRepository<ExpensesDetailEntity, Long>{
    
}
