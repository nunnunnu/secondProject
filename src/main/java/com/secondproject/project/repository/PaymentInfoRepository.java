package com.secondproject.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.PaymentInfoEntity;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long>{
    List<PaymentInfoEntity> findAll();
    PaymentInfoEntity findByPiName(String PiName);
}
