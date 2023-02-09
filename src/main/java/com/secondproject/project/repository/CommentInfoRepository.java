package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.secondproject.project.entity.CommentInfoEntity;

@Repository
public interface CommentInfoRepository extends JpaRepository<CommentInfoEntity, Long>{
    
}
