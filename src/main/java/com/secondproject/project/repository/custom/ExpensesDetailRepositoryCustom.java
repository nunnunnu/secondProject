package com.secondproject.project.repository.custom;

import java.util.List;

import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.CategoryExpensesVO;

public interface ExpensesDetailRepositoryCustom {
    List<DailyExpensesVO> dailyExpenses(DailyExpensesSearchVO search);

    List<CategoryExpensesVO> CategoryExpenses(DailyExpensesSearchVO search);

    Integer totalSum(DailyExpensesSearchVO search);
}
