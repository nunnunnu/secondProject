package com.secondproject.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.custom.ExpensesDetailRepositoryCustom;

public interface ExpensesDetailRepository extends JpaRepository<ExpensesDetailEntity, Long>, ExpensesDetailRepositoryCustom{
    List<ExpensesDetailEntity> findByEdMiSeqAndEdDateBetween(MemberInfoEntity member, LocalDate start, LocalDate end);

    List<ExpensesDetailEntity> findByEdMiSeq(MemberInfoEntity member);
    
    @Query("SELECT e FROM ExpensesDetailEntity e join fetch e.edMiSeq m join fetch e.edCateSeq c WHERE e.edMiSeq = :member AND e.edCateSeq = :cate")
    List<ExpensesDetailEntity> findMemberAndCate(@Param("member") MemberInfoEntity member, @Param("cate") CategoryInfoEntity cate);

    
    List<ExpensesDetailEntity> findByEdSeq(ExpensesDetailEntity expenses);
    List<ExpensesDetailEntity> findByEdCateSeq(CategoryInfoEntity cate);
    
    @Query("SELECT e FROM ExpensesDetailEntity e join fetch e.edMiSeq m join fetch e.edCateSeq s WHERE e.edMiSeq = :member AND e.edSeq = :edSeq")
    ExpensesDetailEntity findMemberAndEdSeq(@Param("member") MemberInfoEntity member, @Param("edSeq") Long edSeq);

    // @Query("SELECT e FROM ExpensesDetailEntity e join fetch e.edMiSeq m join fetch e.edSeq c join fetch e.edCateSeq s WHERE e.edMiSeq = :member AND e.edSeq = :edSeq AND e.edCateSeq = :cateSeq")
    // 자기 자신에서 join fetch 걸면 오류남 -> 서브쿼리는 아직 안되니까 native로 쓰렴 // List로 가져오면
    // List<ExpensesDetailEntity> findMemberAndEdSeqAndCateSeq(@Param("member") MemberInfoEntity member, @Param("edSeq")ExpensesDetailEntity edSeq, @Param("cateSeq") CategoryInfoEntity cateSeq);

}
