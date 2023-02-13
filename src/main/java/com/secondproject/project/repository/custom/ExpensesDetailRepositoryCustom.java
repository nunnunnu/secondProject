package com.secondproject.project.repository.custom;

import java.util.List;

import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.YearExpensesVO;

public interface ExpensesDetailRepositoryCustom {
    List<DailyExpensesVO> dailyExpenses(DailyExpensesSearchVO search);

    List<CategoryExpensesVO> CategoryExpenses(DailyExpensesSearchVO search);

    Integer totalSum(DailyExpensesSearchVO search);

    List<YearExpensesVO> yearSum(DailyExpensesSearchVO search);



}
