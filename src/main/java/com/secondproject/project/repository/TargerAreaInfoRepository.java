package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.TargetAreaInfoEntity;

public interface TargerAreaInfoRepository extends JpaRepository<TargetAreaInfoEntity, Long>{

    // findByTaiMinCostLessThanAndTaiMaxCostGreaterThan 이름이 너무 길어서 jpql로 만들었음
    @Query("select t from TargetAreaInfoEntity t where taiMinCost <= :cost and taiMaxCost >= :cost")
    TargetAreaInfoEntity findTarget(@Param("cost") Integer cost);

}
