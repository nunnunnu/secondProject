package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.BoardImageEntity;
import com.secondproject.project.entity.BoardInfoEntity;

public interface BoardImageRepository extends JpaRepository<BoardImageEntity, Long> {

    BoardImageEntity findByBimgUri(String uri);

    List<BoardImageEntity> findByBimgBiSeqAndBimgSeqIn(BoardInfoEntity board, List<Long> seqs);
    List<BoardImageEntity> findByBimgBiSeq(BoardInfoEntity board);
    
}
