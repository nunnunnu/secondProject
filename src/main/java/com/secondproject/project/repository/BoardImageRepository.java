package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.BoardImageEntity;

public interface BoardImageRepository extends JpaRepository<BoardImageEntity, Long> {
    
}
