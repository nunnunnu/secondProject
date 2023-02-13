package com.secondproject.project.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.custom.ExpensesDetailRepositoryCustom;
import com.secondproject.project.vo.ExpensesMonthVO;

public interface ExpensesDetailRepository extends JpaRepository<ExpensesDetailEntity, Long>, ExpensesDetailRepositoryCustom{
    List<ExpensesDetailEntity> findByEdMiSeqAndEdDateBetween(MemberInfoEntity member, LocalDate start, LocalDate end);

    List<ExpensesDetailEntity> findByEdMiSeq(MemberInfoEntity member);

    @Query("SELECT e FROM ExpensesDetailEntity e join fetch e.edMiSeq m join fetch e.edCateSeq c WHERE e.edMiSeq = :member AND e.edCateSeq = :cateSeq")
    List<ExpensesDetailEntity> findMemberAndCate(@Param("member") MemberInfoEntity member, @Param("cateSeq") CategoryInfoEntity cateSeq);

    //월별 총 지출금액조회
//      @Query(value = "select user.id as id, user.name as name, user.phone as phone, json_unquote(json_extract(user.register_info,'$.id')) as registerInfo , str_to_date(json_unquote(json_extract(user.register_info, '$.date')), '%Y-%m-%d') as registerDate, user.dept_id as deptId, dept.name as deptName " +
//     "from user user " +
//     "left outer join dept dept on user.dept_id = dept.id " +
//     "where match (user.name) against (:name in boolean mode) > 0", nativeQuery = true)
//      List<UserNativeVo> findTest4(@Param("name") String name);

// select mi.mi_seq ,mi.mi_nickname, mi.mi_email,
// date_format(ed_date, '%y-%m') m, sum(ed_amount) 
// from expenses_detail ed 
// left join member_info mi on mi.mi_seq = ed.ed_mi_seq 
// where ed_mi_seq = 1 
// group by m order by m desc;

    // 월간 지출내역 수정중
    // @Query(value =
    // "select mi.mi_seq as seq, mi.mi_nickname as nickname, date_format(ed_date,'%y-%m') as eddate, sum(ed_amout) as edamount"
    // +"from expenses_detail ed "
    // +"left join member_info mi on mi.mi_seq = ed.ed_mi_seq"
    // +"where ed_mi_seq = miseq"
    // , nativeQuery = true)
    // List<ExpensesMonthVO> searchMonth(@Param("miseq") Long miSeq);
}
