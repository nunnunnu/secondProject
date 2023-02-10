package com.secondproject.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.custom.ExpensesDetailRepositoryCustom;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.ExpensesDetailListVO;

public interface ExpensesDetailRepository extends JpaRepository<ExpensesDetailEntity, Long>, ExpensesDetailRepositoryCustom{
    List<ExpensesDetailEntity> findByEdMiSeqAndEdDateBetween(MemberInfoEntity member, LocalDate start, LocalDate end);

    //월별 총 지출금액조회
//      @Query(value = "select user.id as id, user.name as name, user.phone as phone, json_unquote(json_extract(user.register_info,'$.id')) as registerInfo , str_to_date(json_unquote(json_extract(user.register_info, '$.date')), '%Y-%m-%d') as registerDate, user.dept_id as deptId, dept.name as deptName " +
//     "from user user " +
//     "left outer join dept dept on user.dept_id = dept.id " +
//     "where match (user.name) against (:name in boolean mode) > 0", nativeQuery = true)
//      List<UserNativeVo> findTest4(@Param("name") String name);

// select mi.mi_nickname, mi.mi_email, date_format(ed_date, '%y-%m') m, sum(ed_amount) 
// from expenses_detail ed 
// left join member_info mi on mi.mi_seq = ed.ed_mi_seq 
// where ed_mi_seq = 1 
// group by m order by m desc;

// @Query(
//     value = "select new com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO(ms.seq, oi.product.name, oi.option, oi.price, ms.stock) from OptionInfoEntity oi left join MarketStockEntity ms on ms.option.seq = oi.seq and ms.market.seq = :marketInfoSeq"
//     )
// List<LocalMarketOptionStockVO> getOptionList(@Param("marketInfoSeq") Long marketInfoSeq);

// @Query(value = "SELECT new com.greenart.firstproject.vo.superadmin.AdminUserVO(u.seq, u.name, u.email, u.nickname, u.birth, u.phone, u.address, u.status, u.regDt) FROM UserEntity u WHERE u.seq = :seq")
// AdminUserVO findAdminUserVOBySeq(@Param("seq") Long seq);

// @Query(
//     value = "select new com.secondproject.project.vo.ExpensesDetailListVO(mi.miSeq, mi.miNickName, mi.miEmail, edMonthDate(date_format(ed_date, '%y-%m')), edTotalAmount(sum(ed_amount))) from ExpensesDetailEntity ed left join MemberInfoEntity mi on mi.miSeq = ed.edMiSeq =: miSeq"
//     )
//     List<ExpensesDetailListVO> getExpensesMonthList(@Param("miSeq") Long miSeq);

// List<DailyExpensesVO> dailyExpenses(DailyExpensesSearchVO search);
}
