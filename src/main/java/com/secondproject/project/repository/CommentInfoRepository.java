package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.CommentInfoEntity;

public interface CommentInfoRepository extends JpaRepository<CommentInfoEntity, Long>{
    
}
