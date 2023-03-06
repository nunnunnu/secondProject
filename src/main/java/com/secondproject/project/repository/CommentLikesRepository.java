package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CommentLikesEntity;
import com.secondproject.project.entity.MemberInfoEntity;

public interface CommentLikesRepository extends JpaRepository<CommentLikesEntity, Long>{

    // @Query("select count(c.clStatus) from CommentLikesEntity c where c.clStatus = 0 and c.clBiSeq = :seq")
    // Long countLike(@Param("seq") BoardInfoEntity board);
    Long countByClStatusAndClBiSeq(int status, BoardInfoEntity board);

    Long countByClMiSeqAndClBiSeq(MemberInfoEntity member, BoardInfoEntity board);

    
}
