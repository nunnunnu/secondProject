package com.secondproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.secondproject.project.entity.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{    
}
