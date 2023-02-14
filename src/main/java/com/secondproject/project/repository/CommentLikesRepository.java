package com.secondproject.project.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CommentLikesEntity;

public interface CommentLikesRepository extends JpaRepository<CommentLikesEntity, Long>{

    // @Query("select count(c.clStatus) from CommentLikesEntity c where c.clStatus = 0 and c.clBiSeq = :seq")
    // Long countLike(@Param("seq") BoardInfoEntity board);
    Long countByClStatusAndClBiSeq(int status, BoardInfoEntity board);
}
