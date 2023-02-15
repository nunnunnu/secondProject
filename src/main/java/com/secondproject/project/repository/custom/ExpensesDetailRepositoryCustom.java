package com.secondproject.project.repository.custom;

import java.util.List;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.YearExpensesVO;
import com.secondproject.project.vo.expenses.UserCompare;

public interface ExpensesDetailRepositoryCustom {
    List<DailyExpensesVO> dailyExpenses(DailyExpensesSearchVO search);

    List<CategoryExpensesVO> CategoryExpenses(DailyExpensesSearchVO search);

    Integer totalSum(DailyExpensesSearchVO search);

    List<YearExpensesVO> yearSum(DailyExpensesSearchVO search);

    List<UserCompare> userCompareQuery(DailyExpensesSearchVO search, List<MemberInfoEntity> members);



}
