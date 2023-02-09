package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.secondproject.project.entity.MemberInfoEntity;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{    
}
