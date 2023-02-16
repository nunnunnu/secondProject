package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{    
    public Integer countByMiEmail(String miEmail);
    public Integer countByMiNickname(String miNickname);
    public MemberInfoEntity findByMiEmailAndMiPwd(String miEmail, String miPwd);
    public MemberInfoEntity findByMiSeq(Long miSeq);

    List<MemberInfoEntity> findByMiTargetAmountBetween(Integer mimCost, Integer maxCost);
}
