package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.TargetAreaInfoEntity;

public interface BoardInfoRepository extends JpaRepository<BoardInfoEntity, Long>{
    BoardInfoEntity findByBiMiSeqAndBiSeq(Long member, Long post);

    // @EntityGraph( attributePaths = {"comment"})
    Page<BoardInfoEntity> findByBiTaiSeqOrderByBiRegDtDesc(TargetAreaInfoEntity target, Pageable page);

    // @EntityGraph( attributePaths = {"comment"})
    Page<BoardInfoEntity> findByBiStatusOrderByBiRegDtDesc(Integer status, Pageable page);
    
    // @EntityGraph( attributePaths = {"imgs"})
    BoardInfoEntity findByBiSeqAndBiStatus(Long seq, Integer status);
    
    // @EntityGraph( attributePaths = {"comment"})
    Page<BoardInfoEntity> findByBiTaiSeqAndBiTitleContains(TargetAreaInfoEntity target, String keyword, Pageable page);

    // @EntityGraph( attributePaths = {"comment"})
    Page<BoardInfoEntity> findByBiTitleContains(String keyword, Pageable page);

    BoardInfoEntity findByBiSeq(Long biSeq);

}
