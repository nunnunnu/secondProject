package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.BoardInfoEntity;

public interface BoardInfoRepository extends JpaRepository<BoardInfoEntity, Long>{
    BoardInfoEntity findByBiMiSeqAndBiSeq(Long member, Long post);
}
