package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.CommentLikesEntity;

public interface CommentLikesRepository extends JpaRepository<CommentLikesEntity, Long>{
    
}
