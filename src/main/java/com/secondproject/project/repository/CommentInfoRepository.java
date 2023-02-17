package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CommentInfoEntity;
import com.secondproject.project.entity.MemberInfoEntity;

public interface CommentInfoRepository extends JpaRepository<CommentInfoEntity, Long>{
    List<CommentInfoEntity> findByMemberInfoEntity(MemberInfoEntity member);
    List<CommentInfoEntity> findByBoardInfoEntity(BoardInfoEntity board);

}
