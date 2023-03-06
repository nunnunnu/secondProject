package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.secondproject.project.entity.BoardImageEntity;
import com.secondproject.project.entity.BoardInfoEntity;

public interface BoardImageRepository extends JpaRepository<BoardImageEntity, Long> {

    BoardImageEntity findByBimgName(String uri);

    List<BoardImageEntity> findByBimgBiSeqAndBimgSeqIn(BoardInfoEntity board, List<Long> seqs);
    List<BoardImageEntity> findByBimgBiSeq(BoardInfoEntity board);

    @Transactional
    @Modifying
    @Query("update BoardImageEntity b set b.bimgStatus=1 where b.bimgBiSeq=:board")
    void boardDeleteQuery(@Param("board") BoardInfoEntity board);
    
    
}
