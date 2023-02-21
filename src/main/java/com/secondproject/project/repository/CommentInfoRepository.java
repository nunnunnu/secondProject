package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CommentInfoEntity;
import com.secondproject.project.entity.MemberInfoEntity;

public interface CommentInfoRepository extends JpaRepository<CommentInfoEntity, Long>{
    List<CommentInfoEntity> findByMemberInfoEntity(MemberInfoEntity member);
    List<CommentInfoEntity> findByBoardInfoEntity(BoardInfoEntity board);
    
    CommentInfoEntity findByCiSeq(Long ciSeq);

    // @EntityGraph( attributePaths = {"memberInfoEntity"})
    CommentInfoEntity findByCiSeqAndCiStatusAndMemberInfoEntity(Long ciSeq, Integer ciStatus, MemberInfoEntity member);

    //jpql is null 은 null인 것만 찾아서 상위댓글용
    @Query("SELECT c FROM CommentInfoEntity c join fetch c.memberInfoEntity m WHERE c.boardInfoEntity = :board AND c.commentInfoEntity IS Null")
    List<CommentInfoEntity> findBoard(@Param("board") BoardInfoEntity board);

    // jpa findBy 댓댓글용
    @EntityGraph( attributePaths = {"memberInfoEntity"})
    List<CommentInfoEntity> findByCommentInfoEntityOrderByCiRegDt(CommentInfoEntity comment);

}
