package com.secondproject.project.repository.custom;

import java.util.List;

import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;

public interface ExpensesDetailRepositoryCustom {
    List<DailyExpensesVO> dailyExpenses(DailyExpensesSearchVO search);
}
