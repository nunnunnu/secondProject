package com.secondproject.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.custom.ExpensesDetailRepositoryCustom;

public interface ExpensesDetailRepository extends JpaRepository<ExpensesDetailEntity, Long>, ExpensesDetailRepositoryCustom{
    List<ExpensesDetailEntity> findByEdMiSeqAndEdDateBetween(MemberInfoEntity member, LocalDate start, LocalDate end);
}
